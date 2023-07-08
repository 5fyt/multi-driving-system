package com.xiaoyao.hxds.customer.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("tb_customer_car")
public class CustomerCarEntity {

    private Long id;

    /**
     * 用户主键值
     */
    private Long customerId;

    /**
     * 车牌号
     */
    private String carPlate;

    /**
     * 车型
     */
    private String carType;
}
