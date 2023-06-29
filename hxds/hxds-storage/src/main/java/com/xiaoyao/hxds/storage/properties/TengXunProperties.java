package com.xiaoyao.hxds.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("tengxun.cloud")
public class TengXunProperties {
    private String secretId;
    private String secretKey;
    private String bucket;
    private String region;
}
