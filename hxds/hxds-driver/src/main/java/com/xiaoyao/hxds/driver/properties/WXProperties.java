package com.xiaoyao.hxds.driver.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("wx")
public class WXProperties {
    /**
     * 小程序appid
     */
    private String appid;

    /**
     * 小程序secret
     */
    private String secret;
}
