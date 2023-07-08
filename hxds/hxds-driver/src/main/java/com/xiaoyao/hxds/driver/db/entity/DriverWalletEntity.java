package com.xiaoyao.hxds.driver.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@TableName("tb_driver_wallet")
public class DriverWalletEntity {

    private Long id;

    /**
     * 司机主键值
     */
    private Long driverId;

    /**
     * 钱包余额
     */
    private BigDecimal balance;

    /**
     * 支付密码
     */
    private String password;
}
