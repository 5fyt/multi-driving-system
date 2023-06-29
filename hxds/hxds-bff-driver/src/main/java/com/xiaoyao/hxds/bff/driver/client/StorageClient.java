package com.xiaoyao.hxds.bff.driver.client;

import com.xiaoyao.hxds.common.dto.bff.driver.service.DeleteFilesDTO;
import com.xiaoyao.hxds.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("hxds-storage")
public interface StorageClient {
    @PostMapping(value = "/storage/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R upload(@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/storage/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    R delete(@RequestBody DeleteFilesDTO dto);
}
