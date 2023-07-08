package com.xiaoyao.hxds.storage.controller;

import com.xiaoyao.hxds.common.result.R;
import com.xiaoyao.hxds.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        String key = storageService.upload(file);
        return R.ok().put("key", key);
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Map<String, Object> param) {
        storageService.delete(param);
        return R.ok();
    }

    @PostMapping("/temp-url")
    public R TempUrl(@RequestBody Map<String, Object> param) {
        String url = storageService.getTempUrl(param);
        return R.ok().put("url", url);
    }
}
