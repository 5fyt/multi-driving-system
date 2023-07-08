package com.xiaoyao.hxds.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface StorageService {
    /**
     * 上传文件，返回文件的唯一标识。
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * 删除文件
     */
    void delete(Map<String, Object> param);

    /**
     * 生成临时访问链接
     */
    String getTempUrl(Map<String, Object> param);
}
