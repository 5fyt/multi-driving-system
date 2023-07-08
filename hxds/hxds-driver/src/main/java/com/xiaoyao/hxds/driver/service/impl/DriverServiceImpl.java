package com.xiaoyao.hxds.driver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyao.hxds.common.result.Assert;
import com.xiaoyao.hxds.common.result.E;
import com.xiaoyao.hxds.driver.db.entity.DriverAccountEntity;
import com.xiaoyao.hxds.driver.db.entity.DriverSettingsEntity;
import com.xiaoyao.hxds.driver.db.entity.DriverSummaryEntity;
import com.xiaoyao.hxds.driver.db.entity.DriverWalletEntity;
import com.xiaoyao.hxds.driver.db.mapper.DriverAccountMapper;
import com.xiaoyao.hxds.driver.db.mapper.DriverSettingsMapper;
import com.xiaoyao.hxds.driver.db.mapper.DriverSummaryMapper;
import com.xiaoyao.hxds.driver.db.mapper.DriverWalletMapper;
import com.xiaoyao.hxds.driver.properties.WXProperties;
import com.xiaoyao.hxds.driver.service.DriverService;
import com.xiaoyao.hxds.driver.util.FaceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final WXProperties wxProperties;
    private final DriverAccountMapper driverAccountMapper;
    private final DriverWalletMapper driverWalletMapper;
    private final DriverSummaryMapper driverSummaryMapper;
    private final DriverSettingsMapper driverSettingsMapper;
    private final FaceUtil faceUtil;

    @Override
    public long register(Map<String, Object> prama) {
        String openid = getOpenid(MapUtil.getStr(prama, "code"));

        // 确保openid没有被注册过
        QueryWrapper<DriverAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        Long count = driverAccountMapper.selectCount(queryWrapper);
        Assert.isTrue(count == 0, E.OPENID_EXIST);

        DriverAccountEntity driverAccount = new DriverAccountEntity();
        driverAccount.setOpenid(openid);
        driverAccount.setNickname(MapUtil.getStr(prama, "nickname"));
        driverAccount.setPhoto(MapUtil.getStr(prama, "photo"));
        driverAccount.setCreateTime(LocalDateTime.now());
        driverAccount.setUpdateTime(LocalDateTime.now());
        driverAccountMapper.insert(driverAccount);

        DriverWalletEntity driverWallet = new DriverWalletEntity();
        driverWallet.setDriverId(driverAccount.getId());
        driverWalletMapper.insert(driverWallet);

        DriverSummaryEntity driverSummary = new DriverSummaryEntity();
        driverSummary.setDriverId(driverAccount.getId());
        driverSummaryMapper.insert(driverSummary);

        DriverSettingsEntity driverSettings = new DriverSettingsEntity();
        driverSettings.setDriverId(driverAccount.getId());
        driverSettingsMapper.insert(driverSettings);

        return driverAccount.getId();
    }

    @Override
    public void authenticate(Map<String, Object> param) {
        // 确保司机没有正在审核中
        QueryWrapper<DriverAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("status").eq("id", MapUtil.getLong(param, "driverID"));
        DriverAccountEntity driverAccount = driverAccountMapper.selectOne(queryWrapper);
        Assert.isTrue(driverAccount.getStatus() != 1, E.AUTHENTICATING);

        BeanUtil.copyProperties(param, driverAccount);
        driverAccount.setId(MapUtil.getLong(param, "driverID"));
        driverAccount.setStatus((byte) 1);
        driverAccount.setUpdateTime(LocalDateTime.now());
        driverAccountMapper.updateById(driverAccount);
    }

    @Override
    public void archive(Map<String, Object> prama) {
        long driverID = MapUtil.getLong(prama, "driverID");
        String image = MapUtil.getStr(prama, "image");

        // 上传认证信息后才可以上传人脸存档
        QueryWrapper<DriverAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("status", "name").eq("id", driverID);
        DriverAccountEntity driverAccount = driverAccountMapper.selectOne(queryWrapper);
        Assert.isTrue(driverAccount.getStatus() != 0, E.NOT_AUTHENTICATED);

        faceUtil.createFaceModel(String.valueOf(driverID), driverAccount.getName(), image);
        UpdateWrapper<DriverAccountEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("archive", 1).eq("id", driverID);
        driverAccountMapper.update(null, updateWrapper);
    }

    @Override
    public Map<String, Object> login(String code) {
        // 确保司机已注册
        String openid = getOpenid(code);
        QueryWrapper<DriverAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "status", "archive").eq("openid", openid);
        List<Map<String, Object>> maps = driverAccountMapper.selectMaps(queryWrapper);
        Assert.isTrue(maps.size() == 1, E.NOT_REGISTERED);

        Map<String, Object> map = maps.get(0);
        map.replace("archive", map.get("archive").equals(Boolean.TRUE) ? 1 : 0);
        return map;
    }

    @Override
    public Map<String, Object> profile(long driverID) {
        QueryWrapper<DriverAccountEntity> q1 = new QueryWrapper<>();
        q1.select("nickname", "photo", "status", "create_time", "archive")
                .eq("id", driverID);
        DriverAccountEntity account = driverAccountMapper.selectOne(q1);

        QueryWrapper<DriverSummaryEntity> q2 = new QueryWrapper<>();
        q2.select("level", "total_order", "week_order", "week_comment", "appeal")
                .eq("driver_id", driverID);
        DriverSummaryEntity summary = driverSummaryMapper.selectOne(q2);

        QueryWrapper<DriverWalletEntity> q3 = new QueryWrapper<>();
        q3.select("balance").eq("driver_id", driverID);
        DriverWalletEntity wallet = driverWalletMapper.selectOne(q3);

        Map<String, Object> map = new HashMap<>();
        BeanUtil.beanToMap(account, map, false, true);
        BeanUtil.beanToMap(summary, map, false, true);
        BeanUtil.beanToMap(wallet, map, false, true);
        map.replace("archive", map.get("archive").equals(Boolean.TRUE) ? 1 : 0);
        // 防止balance精度丢失，转为字符串。
        map.replace("balance", map.get("balance").toString());
        return map;
    }

    @Override
    public Map<String, Object> settings(long driverID) {
        QueryWrapper<DriverSettingsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("is_auto_accept", "orientation", "is_auto_listen",
                        "order_distance", "range_distance")
                .eq("driver_id", driverID);
        DriverSettingsEntity settings = driverSettingsMapper.selectOne(queryWrapper);

        return BeanUtil.beanToMap(settings, false, true);
    }

    @Override
    public Map<String, Object> searchDrivers(Map<String, Object> param) {
        long page = MapUtil.getLong(param, "page");
        long size = MapUtil.getLong(param, "size");
        DriverAccountEntity bean = BeanUtil.toBean(param, DriverAccountEntity.class,
                CopyOptions.create().ignoreNullValue());

        QueryWrapper<DriverAccountEntity> queryWrapper = new QueryWrapper<>(bean);
        queryWrapper.select("id", "name", "gender", "phone", "id_card_number", "emergency_name",
                "emergency_phone", "status", "account_status");
        Page<Map<String, Object>> pageData = driverAccountMapper.selectMapsPage(Page.of(page, size), queryWrapper);

        List<Map<String, Object>> data = new ArrayList<>();
        pageData.getRecords().forEach(entity -> data.add(BeanUtil.beanToMap(entity, new HashMap<>(),
                CopyOptions.create().setFieldNameEditor(StrUtil::toCamelCase))));
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("total", pageData.getTotal());
        map.put("pages", pageData.getPages());
        return map;
    }

    @Override
    public Map<String, Object> getAuthenticateInfo(long driverID) {
        QueryWrapper<DriverAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "gender", "birthday", "id_card_number", "id_card_address",
                        "id_card_expire_time", "phone", "email", "address", "emergency_name",
                        "emergency_phone", "driver_card_type", "driver_card_expire_time",
                        "driver_card_issue_time", "id_card_front_image", "id_card_back_image",
                        "id_card_holding_image", "driver_card_front_image", "driver_card_back_image",
                        "driver_card_holding_image")
                .eq("id", driverID);
        List<Map<String, Object>> maps = driverAccountMapper.selectMaps(queryWrapper);

        Map<String, Object> map = new HashMap<>();
        BeanUtil.copyProperties(maps.get(0), map, CopyOptions.create().setFieldNameEditor(StrUtil::toCamelCase));
        return map;
    }

    private String getOpenid(String code) {
        String response = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", Map.of(
                "appid", wxProperties.getAppid(),
                "secret", wxProperties.getSecret(),
                "js_code", code,
                "grant_type", "authorization_code"));
        JSONObject jsonObject = JSONUtil.parseObj(response);
        return jsonObject.getStr("openid");
        //        return "code-openid";
    }
}
