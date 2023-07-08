package com.xiaoyao.hxds.map.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiaoyao.hxds.map.properties.MapProperties;
import com.xiaoyao.hxds.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final static String DRIVERS_LOCATION = "drivers-location";
    private final static String DRIVER_ACCEPT_PREFIX = "driver-accept:";

    private final MapProperties mapProperties;
    private final StringRedisTemplate redisTemplate;

    @Override
    public Map<String, Object> calculateDrivingLine(Map<String, Object> param) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("from", MapUtil.getStr(param, "from"));
        paramMap.put("to", MapUtil.getStr(param, "to"));
        paramMap.put("key", mapProperties.getKey());
        String response = HttpUtil.get("https://apis.map.qq.com/ws/direction/v1/driving/", paramMap);

        JSONObject jsonObject = JSONUtil.parseObj(response);
        JSONObject line = jsonObject.getJSONObject("result").getJSONArray("routes").getJSONObject(0);

        Map<String, Object> map = new HashMap<>();
        map.put("distance", line.getInt("distance"));
        map.put("duration", line.getInt("duration"));
        return map;
    }

    @Override
    public void updateDriverLocation(Map<String, Object> param) {
        // 更新司机的坐标信息
        Point point = new Point(MapUtil.getDouble(param, "longitude"), MapUtil.getDouble(param, "latitude"));
        redisTemplate.opsForGeo().add(DRIVERS_LOCATION, point, MapUtil.getStr(param, "driverID"));

        // 更新司机的接单信息
        String orientation = MapUtil.getStr(param, "orientation");
        redisTemplate.opsForValue().set(DRIVER_ACCEPT_PREFIX + MapUtil.getStr(param, "driverID"),
                MapUtil.getStr(param, "rangeDistance") + ";"
                        + MapUtil.getStr(param, "orderDistance") + ";"
                        + (StrUtil.isBlank(orientation) ? "none" : orientation),
                1, TimeUnit.MINUTES);
    }

    @Override
    public void deleteDriverLocation(Map<String, Object> param) {
        redisTemplate.opsForZSet().remove(DRIVERS_LOCATION, MapUtil.getStr(param, "driverID"));
        redisTemplate.delete(DRIVER_ACCEPT_PREFIX + MapUtil.getStr(param, "driverID"));
    }

    @Override
    public Map<String, Object> searchNearbyDriver(Map<String, Object> param) {
        Double fromLongitude = MapUtil.getDouble(param, "fromLongitude");
        Double fromLatitude = MapUtil.getDouble(param, "fromLatitude");
        Double toLongitude = MapUtil.getDouble(param, "toLongitude");
        Double toLatitude = MapUtil.getDouble(param, "toLatitude");
        Double drivingDistance = MapUtil.getDouble(param, "drivingDistance");

        // 存放结果的集合
        ArrayList<Map<String, Object>> drivers = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("drivers", drivers);

        // 获取出发点附近5千米以内的所有司机
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> results = redisTemplate.opsForGeo().search(
                DRIVERS_LOCATION, GeoReference.fromCoordinate(fromLongitude, fromLatitude),
                new Distance(5, Metrics.KILOMETERS),
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance()
        ).getContent();
        if (results.size() == 0) {
            return map;
        }
        for (int i = 0; i < results.size(); i++) {
            String driverID = results.get(i).getContent().getName();
            String acceptInfo = redisTemplate.opsForValue().get(DRIVER_ACCEPT_PREFIX + driverID);
            // 剔除掉不在线的司机
            if (acceptInfo == null) {
                redisTemplate.opsForGeo().remove(DRIVERS_LOCATION, driverID);
                continue;
            }
            String[] acceptInfos = acceptInfo.split(";");

            // 判断接单距离是否在范围内
            if (Integer.parseInt(acceptInfos[0]) < results.get(i).getDistance().getValue()) {
                continue;
            }
            // 判断行驶距离是否在范围内
            if (Integer.parseInt(acceptInfos[1]) != 0 && Integer.parseInt(acceptInfos[1]) < drivingDistance) {
                continue;
            }
            // 判断目的地是否在定向接单范围内
            if (!acceptInfos[2].equals("none")) {
                double longitude = Double.parseDouble(acceptInfos[2].split(",")[0]);
                double latitude = Double.parseDouble(acceptInfos[2].split(",")[1]);
                if (calculateDistance(longitude, latitude, toLongitude, toLatitude) > 5) {
                    continue;
                }
            }

            Map<String, Object> driver = new HashMap<>();
            driver.put("driverID", driverID);
            driver.put("distance", BigDecimal.valueOf(results.get(i).getDistance().getValue())
                    .setScale(3, RoundingMode.HALF_UP).toString());
            drivers.add(driver);
        }
        return map;
    }

    /**
     * 计算两个经纬度坐标点之间的距离，单位为千米。
     */
    private double calculateDistance(double startLong, double startLat, double endLong, double endLat) {
        double dLat = Math.toRadians(endLat - startLat);
        double dLong = Math.toRadians(endLong - startLong);
        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLong / 2), 2) * Math.cos(startLat) * Math.cos(endLat);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6371 * c;
    }
}
