package com.xiaoyao.hxds.order.service;

import java.util.Map;

public interface OrderService {
    /**
     * 获取司机今日营业数据
     */
    Map<String, Object> getDriverTodayBusinessData(long driverID);

    /**
     * 创建订单。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>customerId - String</b></li>
     *    <li><b>carPlate - String</b></li>
     *    <li><b>carType - String</b></li>
     *    <li><b>startCoordinate - String</b></li>
     *    <li><b>startPlace - String</b></li>
     *    <li><b>endCoordinate - String</b></li>
     *    <li><b>endPlace - String</b></li>
     *    <li><b>expectsMileage - Integer</b></li>
     *    <li><b>expectsTime - Integer</b></li>
     *    <li><b>expectsFee - String</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>orderID - String</b></li>
     * </ul>
     */
    Map<String, Object> createOrder(Map<String, Object> param);

    /**
     * 抢单。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>orderID - String</b></li>
     *    <li><b>driverID - String</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>result - Integer</b>：0为失败，1为成功</li>
     *    <li><b>customerId - String</b>：（result为1才有）客户ID（result为1才有）</li>
     *    <li><b>createTime - String</b>：（result为1才有）下单时间</li>
     *    <li><b>expectsFee - String</b>：（result为1才有）预计费用</li>
     *    <li><b>carPlate - String</b>：（result为1才有）车牌</li>
     *    <li><b>carType - String</b>：（result为1才有）车型</li>
     * </ul>
     */
    Map<String, Object> acceptOrder(Map<String, Object> param);

    /**
     * 判断订单是否被抢。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>orderID - String</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>result - Integer</b>：0为失败，1为成功</li>
     * </ul>
     */
    Map<String, Object> isOrderAccepted(Map<String, Object> param);

    /**
     * 取消订单。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>orderID - String</b></li>
     *    <li><b>customerID - String</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>result - Integer</b>：0为失败，1为成功</li>
     * </ul>
     */
    Map<String, Object> cancelOrder(Map<String, Object> param);
}
