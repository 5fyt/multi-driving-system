package com.xiaoyao.hxds.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageService {
    /**
     * 上传文件，返回文件的唯一标识。
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * 删除文件
     */
    void delete(List<String> keys);
}
