package com.xiaoyao.hxds.map.service;

import java.util.Map;

public interface MapService {
    /**
     * 计算行车路线之间的预计里程和时间。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>from - String</b></li>
     *    <li><b>to - String</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>distance - Integer</b></li>
     *    <li><b>duration - Integer</b></li>
     * </ul>
     */
    Map<String, Object> calculateDrivingLine(Map<String, Object> param);

    /**
     * 更新司机的地图坐标信息。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>driverID - Long</b></li>
     *    <li><b>longitude - String</b></li>
     *    <li><b>latitude - String</b></li>
     *    <li><b>orientation - String</b></li>
     *    <li><b>orderDistance - Integer</b></li>
     *    <li><b>rangeDistance - Integer</b></li>
     * </ul>
     */
    void updateDriverLocation(Map<String, Object> param);

    /**
     * 删除司机的地图坐标信息。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>driverID - Long</b></li>
     * </ul>
     */
    void deleteDriverLocation(Map<String, Object> param);

    /**
     * 搜索附近适合接单的司机。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>fromLongitude - String</b></li>
     *    <li><b>fromLatitude - String</b></li>
     *    <li><b>toLongitude - String</b></li>
     *    <li><b>toLatitude - String</b></li>
     *    <li><b>drivingDistance - Double</b>：行驶路程，单位为千米</li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>drivers - List</b></li>
     *    <ul>
     *        <li><b>driverID - String</b></li>
     *        <li><b>distance - String</b>：离上车点的距离，单位为千米</li>
     *    </ul>
     * </ul>
     */
    Map<String, Object> searchNearbyDriver(Map<String, Object> param);
}
