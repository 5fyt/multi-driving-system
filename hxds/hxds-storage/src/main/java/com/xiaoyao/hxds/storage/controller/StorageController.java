package com.xiaoyao.hxds.storage.controller;

import com.xiaoyao.hxds.common.dto.bff.driver.service.DeleteFilesDTO;
import com.xiaoyao.hxds.common.result.R;
import com.xiaoyao.hxds.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        String key = storageService.upload(file);
        return R.ok()
                .put("key", key);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody DeleteFilesDTO dto) {
        storageService.delete(dto.getKeys());
        return R.ok();
    }
}
