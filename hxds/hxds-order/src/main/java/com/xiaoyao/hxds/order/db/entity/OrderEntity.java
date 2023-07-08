package com.xiaoyao.hxds.order.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@TableName("tb_order")
public class OrderEntity {

    private Long id;

    /**
     * 客户主键值
     */
    private Long customerId;

    /**
     * 订单状态，1为等待司机接单，2为司机已接单，3为司机已到达，4为开始代驾，5为代驾结束，6为已完成
     */
    private Byte status;

    /**
     * 订单日期
     */
    private LocalDate date;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 车牌
     */
    private String carPlate;

    /**
     * 车型
     */
    private String carType;

    /**
     * 出发地经纬度坐标
     */
    private String startCoordinate;

    /**
     * 出发地位置
     */
    private String startPlace;

    /**
     * 目的地经纬度坐标
     */
    private String endCoordinate;

    /**
     * 目的地位置
     */
    private String endPlace;

    /**
     * 预计里程
     */
    private Integer expectsMileage;

    /**
     * 预计时间
     */
    private Integer expectsTime;

    /**
     * 预计订单价格
     */
    private BigDecimal expectsFee;

    /**
     * 司机主键值
     */
    private Long driverId;

    /**
     * 司机接单时间
     */
    private LocalDateTime acceptTime;

    /**
     * 司机到达时间
     */
    private LocalDateTime arriveTime;

    /**
     * 代驾开始时间
     */
    private LocalDateTime startTime;

    /**
     * 代驾结束时间
     */
    private LocalDateTime endTime;

    /**
     * 实际里程
     */
    private BigDecimal realMileage;

    /**
     * 实际订单价格
     */
    private BigDecimal realFee;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;

    /**
     * 付款日期
     */
    private LocalDate payDate;

    /**
     * 备注信息
     */
    private String note;
}
