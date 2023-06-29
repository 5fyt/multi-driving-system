package com.xiaoyao.hxds.driver.util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.CreatePersonRequest;
import com.xiaoyao.hxds.common.result.HxdsException;
import com.xiaoyao.hxds.driver.properties.TengXunProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FaceUtil {
    private final TengXunProperties properties;

    /**
     * 创建人脸模型
     */
    public void createFaceModel(String driverId, String name, String imageBase64) {
        try{
            Credential cred = new Credential(properties.getSecretId(), properties.getSecretKey());
            IaiClient client = new IaiClient(cred, "ap-nanjing");

            CreatePersonRequest req = new CreatePersonRequest();
            req.setGroupId(properties.getGroupId());
            req.setPersonId(driverId);
            req.setPersonName(name);
            req.setImage(imageBase64);
            client.CreatePerson(req);
        }catch (TencentCloudSDKException e) {
            throw new HxdsException(e.getMessage());
        }
    }
}
