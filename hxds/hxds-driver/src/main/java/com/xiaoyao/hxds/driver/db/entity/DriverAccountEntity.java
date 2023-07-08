package com.xiaoyao.hxds.driver.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@TableName("tb_driver_account")
public class DriverAccountEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 微信小程序openid
     */
    private String openid;

    /**
     * 微信昵称
     */
    private String nickname;

    /**
     * 微信头像地址
     */
    private String photo;

    /**
     * 状态，0为未认证，1为待审核，2为已认证
     */
    private Byte status;

    /**
     * 是否上传人脸照片
     */
    private Boolean archive;

    /**
     * 账户状态，1为正常，2为禁用，3为降低接单量
     */
    private Byte accountStatus;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 身份证号
     */
    private String idCardNumber;

    /**
     * 身份证地址
     */
    private String idCardAddress;

    /**
     * 身份证过期日期
     */
    private LocalDate idCardExpireTime;

    /**
     * 身份证正面照片
     */
    private String idCardFrontImage;

    /**
     * 身份证背面照片
     */
    private String idCardBackImage;

    /**
     * 手持身份证照片
     */
    private String idCardHoldingImage;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 紧急联系人
     */
    private String emergencyName;

    /**
     * 紧急联系人手机号
     */
    private String emergencyPhone;

    /**
     * 驾驶证类型
     */
    private String driverCardType;

    /**
     * 驾驶证过期日期
     */
    private LocalDate driverCardExpireTime;

    /**
     * 驾驶证颁发日期
     */
    private LocalDateTime driverCardIssueTime;

    /**
     * 驾驶证正面照片
     */
    private String driverCardFrontImage;

    /**
     * 驾驶证背面照片
     */
    private String driverCardBackImage;

    /**
     * 手持驾驶证照片
     */
    private String driverCardHoldingImage;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
