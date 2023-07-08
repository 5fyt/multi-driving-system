package com.xiaoyao.hxds.driver.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("tb_driver_settings")
public class DriverSettingsEntity {

    private Long id;

    private Long driverId;

    /**
     * 是否自动抢单
     */
    private Boolean isAutoAccept;

    /**
     * 定向接单
     */
    private String orientation;

    /**
     * 是否自动听单
     */
    private Boolean isAutoListen;

    /**
     * 驾驶距离上限，单位为千米，0表示不限
     */
    private Integer orderDistance;

    /**
     * 接单距离上限，单位为千米
     */
    private Integer rangeDistance;
}
