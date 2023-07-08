package com.xiaoyao.hxds.customer.service;

import java.util.Map;

public interface CarService {
    /**
     * 添加车辆。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>customerId - Long</b></li>
     *    <li><b>carPlate - String</b></li>
     *    <li><b>carType - String</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>cars - List</b>
     *    <ul>
     *        <li><b>id - String</b></li>
     *        <li><b>carPlate - String</b></li>
     *        <li><b>carType - String</b></li>
     *    </ul>
     *    </li>
     * </ul>
     */
    Map<String, Object> addCar(Map<String, Object> param);

    /**
     * 删除车辆。
     * <p>
     * 参数列表：
     * <ul>
     *    <li><b>customerId - Long</b></li>
     *    <li><b>id - Long</b></li>
     * </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>cars - List</b>
     *    <ul>
     *        <li><b>id - String</b></li>
     *        <li><b>carPlate - String</b></li>
     *        <li><b>carType - String</b></li>
     *    </ul>
     *    </li>
     * </ul>
     */
    Map<String, Object> deleteCar(Map<String, Object> param);

    /**
     * 查看所有的车辆。
     * <p>
     * 参数列表：
     *      <ul>
     *         <li><b>customerId - Long</b></li>
     *      </ul>
     * <p>
     * 返回值列表：
     * <ul>
     *    <li><b>cars - List</b>
     *    <ul>
     *        <li><b>id - String</b></li>
     *        <li><b>carPlate - String</b></li>
     *        <li><b>carType - String</b></li>
     *    </ul>
     *    </li>
     * </ul>
     */
    Map<String, Object> listCars(Map<String, Object> param);
}
