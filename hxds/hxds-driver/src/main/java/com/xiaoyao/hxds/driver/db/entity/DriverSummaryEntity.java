package com.xiaoyao.hxds.driver.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("tb_driver_summary")
public class DriverSummaryEntity {

    private Long id;

    /**
     * 司机主键值
     */
    private Long driverId;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 总订单数
     */
    private Integer totalOrder;

    /**
     * 周订单数
     */
    private Integer weekOrder;

    /**
     * 周好评数
     */
    private Integer weekComment;

    /**
     * 正在申诉数
     */
    private Integer appeal;
}
