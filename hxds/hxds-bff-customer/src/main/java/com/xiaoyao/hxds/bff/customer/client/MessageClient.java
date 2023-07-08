package com.xiaoyao.hxds.bff.customer.client;

import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("hxds-message")
public interface MessageClient {
    @PostMapping("/message/send-message-to-drivers")
    R sendMessageToDrivers(@RequestBody Map<String, Object> param);
}
