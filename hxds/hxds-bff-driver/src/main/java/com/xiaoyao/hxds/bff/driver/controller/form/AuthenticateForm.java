package com.xiaoyao.hxds.bff.driver.controller.form;

import lombok.Data;

@Data
public class AuthenticateForm {
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
    private String birthday;

    /**
     * 身份证号
     */
    private String idCardNumber;

    /**
     * 身份证地址
     */
    private String idCardAddress;

    /**
     * 身份证有效期
     */
    private String idCardExpireTime;

    /**
     * 身份证正面图片的key
     */
    private String idCardFrontImage;

    /**
     * 身份证正面图片的key
     */
    private String idCardBackImage;

    /**
     * 手持身份证图片的key
     */
    private String idCardHoldingImage;

    /**
     * 手机号码
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
     * 紧急联系人电话
     */
    private String emergencyPhone;

    /**
     * 驾驶证类型
     */
    private String driverCardType;

    /**
     * 驾驶证有效期日期
     */
    private String driverCardExpireTime;

    /**
     * 驾驶证初次领证日期
     */
    private String driverCardIssueTime;

    /**
     * 驾驶证正面图片的key
     */
    private String driverCardFrontImage;

    /**
     * 驾驶证背面图片的key
     */
    private String driverCardBackImage;

    /**
     * 手持驾驶证图片的key
     */
    private String driverCardHoldingImage;
}
