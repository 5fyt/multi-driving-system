package com.xiaoyao.hxds.bff.driver.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthenticateForm {
    /**
     * 姓名
     */
    @NotBlank(message = "name不能为空")
    private String name;

    /**
     * 性别
     */
    @NotBlank(message = "gender不能为空")
    @Pattern(regexp = "^男$|^女$", message = "gender只能为男或女")
    private String gender;

    /**
     * 生日
     */
    @NotBlank(message = "birthday不能为空")
    @Pattern(regexp = "\\d{4}-\\d{1,2}-\\d{1,2}", message = "birthday格式不正确")
    private String birthday;

    /**
     * 身份证号
     */
    @NotBlank(message = "idCardNumber不能为空")
    @Pattern(regexp = "\\d{17}(\\d|x|X)", message = "idCardNumber格式不正确")
    private String idCardNumber;

    /**
     * 身份证地址
     */
    @NotBlank(message = "idCardAddress不能为空")
    private String idCardAddress;

    /**
     * 身份证有效期
     */
    @NotBlank(message = "idCardExpireTime不能为空")
    @Pattern(regexp = "\\d{4}-\\d{1,2}-\\d{1,2}", message = "idCardExpireTime格式不正确")
    private String idCardExpireTime;

    /**
     * 身份证正面图片的key
     */
    @NotBlank(message = "idCardFrontImage不能为空")
    private String idCardFrontImage;

    /**
     * 身份证正面图片的key
     */
    @NotBlank(message = "idCardBackImage不能为空")
    private String idCardBackImage;

    /**
     * 手持身份证图片的key
     */
    @NotBlank(message = "idCardHoldingImage不能为空")
    private String idCardHoldingImage;

    /**
     * 手机号码
     */
    @NotBlank(message = "phone不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "phone格式不正确")
    private String phone;

    /**
     * 邮箱
     */
    @NotBlank(message = "email不能为空")
    @Pattern(regexp = "[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}", message = "email格式不正确")
    private String email;

    /**
     * 地址
     */
    @NotBlank(message = "address不能为空")
    private String address;

    /**
     * 紧急联系人
     */
    @NotBlank(message = "emergencyName不能为空")
    private String emergencyName;

    /**
     * 紧急联系人电话
     */
    @NotBlank(message = "emergencyPhone不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "emergencyPhone格式不正确")
    private String emergencyPhone;

    /**
     * 驾驶证类型
     */
    @NotBlank(message = "driverCardType不能为空")
    private String driverCardType;

    /**
     * 驾驶证有效期日期
     */
    @NotBlank(message = "driverCardExpireTime不能为空")
    @Pattern(regexp = "\\d{4}-\\d{1,2}-\\d{1,2}", message = "driverCardExpireTime格式不正确")
    private String driverCardExpireTime;

    /**
     * 驾驶证初次领证日期
     */
    @NotBlank(message = "driverCardIssueTime不能为空")
    @Pattern(regexp = "\\d{4}-\\d{1,2}-\\d{1,2}", message = "driverCardIssueTime格式不正确")
    private String driverCardIssueTime;

    /**
     * 驾驶证正面图片的key
     */
    @NotBlank(message = "driverCardFrontImage不能为空")
    private String driverCardFrontImage;

    /**
     * 驾驶证背面图片的key
     */
    @NotBlank(message = "driverCardBackImage不能为空")
    private String driverCardBackImage;

    /**
     * 手持驾驶证图片的key
     */
    @NotBlank(message = "driverCardHoldingImage不能为空")
    private String driverCardHoldingImage;
}
