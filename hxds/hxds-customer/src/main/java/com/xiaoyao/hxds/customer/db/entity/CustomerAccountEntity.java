package com.xiaoyao.hxds.customer.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@TableName("tb_customer_account")
public class CustomerAccountEntity {

    private Long id;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 微信昵称
     */
    private String nickname;

    /**
     * 性别，可以为男、女、无
     */
    private String gender;

    /**
     * 头像URL地址
     */
    private String photo;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账户状态，1为正常，2为禁用
     */
    private Byte status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
