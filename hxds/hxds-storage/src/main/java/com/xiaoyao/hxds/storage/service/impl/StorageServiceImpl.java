package com.xiaoyao.hxds.storage.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.xiaoyao.hxds.common.result.Assert;
import com.xiaoyao.hxds.common.result.E;
import com.xiaoyao.hxds.storage.properties.TengXunProperties;
import com.xiaoyao.hxds.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final TengXunProperties tengXunProperties;

    @Override
    public String upload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Assert.notBlank(filename, E.ILLEGAL_ARGUMENT);
        String key = UUID.randomUUID().toString().replace("-", "") +
                filename.substring(filename.lastIndexOf("."));
        String contentType = file.getContentType();

        COSCredentials cred = new BasicCOSCredentials(tengXunProperties.getSecretId(),
                tengXunProperties.getSecretKey());
        ClientConfig clientConfig = new ClientConfig(new Region(tengXunProperties.getRegion()));
        COSClient cosClient = new COSClient(cred, clientConfig);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        PutObjectRequest putObjectRequest = new PutObjectRequest(tengXunProperties.getBucket(),
                key, file.getInputStream(), metadata);
        cosClient.putObject(putObjectRequest);

        return key;
    }

    @Override
    public void delete(List<String> keys) {
        COSCredentials cred = new BasicCOSCredentials(tengXunProperties.getSecretId(),
                tengXunProperties.getSecretKey());
        ClientConfig clientConfig = new ClientConfig(new Region(tengXunProperties.getRegion()));
        COSClient cosClient = new COSClient(cred, clientConfig);

        DeleteObjectsRequest request = new DeleteObjectsRequest(tengXunProperties.getBucket());
        request.withKeys(keys.toArray(new String[0]));
        cosClient.deleteObjects(request);
    }
}
