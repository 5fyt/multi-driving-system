package com.xiaoyao.hxds.message.service;

import java.util.Map;

public interface DriverOrderMessageService {
    /**
     * 异步发送新订单通知给指定的司机。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>drivers - List&lt;String&gt;</b>：司机ID列表</li>
     *    <li><b>distances - List&lt;String&gt;</b>：上车距离列表，单位为千米</li>
     *    <li><b>orderID - String</b>：订单ID</li>
     *    <li><b>startPlace - String</b>：出发地</li>
     *    <li><b>endPlace - String</b>：目的地</li>
     *    <li><b>expectsMileage - Integer</b>：预计里程，单位为米</li>
     *    <li><b>expectsTime - Integer</b>：预计时间，单位为分钟</li>
     *    <li><b>expectsFee - String</b>：预计费用</li>
     * </ul>
     */
    void sendOrderToDrivers(Map<String, Object> param);

    /**
     * 获取司机的订单消息。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>driverID - Long</b>：司机ID</li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>messages - List</b></li>
     *    <ul>
     *       <li><b>distance - Double</b>：上车距离，单位为千米</li>
     *       <li><b>orderID - String</b>：订单ID</li>
     *       <li><b>startPlace - String</b>：出发地</li>
     *       <li><b>endPlace - String</b>：目的地</li>
     *       <li><b>expectsMileage - Integer</b>：预计里程，单位为米</li>
     *       <li><b>expectsTime - Integer</b>：预计时间，单位为分钟</li>
     *       <li><b>expectsFee - String</b>：预计费用</li>
     *    </ul>
     * </ul>
     */
    Map<String, Object> getDriverOrdersMessage(Map<String, Object> param);
}
