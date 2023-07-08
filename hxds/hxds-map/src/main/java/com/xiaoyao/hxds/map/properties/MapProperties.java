package com.xiaoyao.hxds.map.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("tengxun.map")
public class MapProperties {
    /**
     * 腾讯地图key
     */
    private String key;
}
