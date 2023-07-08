package com.xiaoyao.hxds.bff.driver.client;

import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient("hxds-storage")
public interface StorageClient {
    @PostMapping(value = "/storage/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R upload(@RequestPart MultipartFile file);

    @PostMapping("/storage/delete")
    R delete(@RequestBody Map<String, Object> param);

    @PostMapping("/storage/temp-url")
    R tempUrl(@RequestBody Map<String, Object> param);
}
