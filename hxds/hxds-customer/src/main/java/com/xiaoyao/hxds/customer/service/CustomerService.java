package com.xiaoyao.hxds.customer.service;

import java.util.Map;

public interface CustomerService {
    /**
     * 用户注册。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>code - String</b></li>
     *    <li><b>nickname - String</b></li>
     *    <li><b>gender - String</b></li>
     *    <li><b>photo - String</b></li>
     *    <li><b>phone - String</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>id - Long</b></li>
     * </ul>
     */
    Map<String, Object> register(Map<String, Object> param);

    /**
     * 用户登录。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>code - String</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>id - Long</b></li>
     * </ul>
     */
    Map<String, Object> login(Map<String, Object> param);

    /**
     * 获取订单中的用户信息。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>customerID - String</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>phone - String</b></li>
     *    <li><b>gender - String</b></li>
     *    <li><b>photo - String</b></li>
     * </ul>
     */
    Map<String, Object> getCustomerInfoInOrder(Map<String, Object> param);
}
