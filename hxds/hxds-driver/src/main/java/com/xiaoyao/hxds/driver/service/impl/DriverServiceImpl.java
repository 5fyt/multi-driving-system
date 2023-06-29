package com.xiaoyao.hxds.driver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiaoyao.hxds.common.dto.bff.driver.service.AuthenticateDTO;
import com.xiaoyao.hxds.common.dto.bff.driver.service.DriverRegisterDTO;
import com.xiaoyao.hxds.common.dto.driver.service.LoginDTO;
import com.xiaoyao.hxds.common.result.Assert;
import com.xiaoyao.hxds.common.result.E;
import com.xiaoyao.hxds.driver.db.entity.DriverAccountEntity;
import com.xiaoyao.hxds.driver.db.mapper.DriverAccountMapper;
import com.xiaoyao.hxds.driver.properties.WXProperties;
import com.xiaoyao.hxds.driver.service.DriverService;
import com.xiaoyao.hxds.driver.util.FaceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaQuery;
import static com.baomidou.mybatisplus.core.toolkit.Wrappers.lambdaUpdate;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final WXProperties wxProperties;
    private final DriverAccountMapper driverAccountMapper;
    private final FaceUtil faceUtil;

    @Override
    public String register(DriverRegisterDTO dto) {
        // 根据微信授权码换取openid
        String openid = getOpenid(dto.getCode());
        Long count = driverAccountMapper.selectCount(lambdaQuery(DriverAccountEntity.class)
                .eq(DriverAccountEntity::getOpenid, openid));
        Assert.isTrue(count == 0, E.OPENID_EXIST);

        // 插入司机记录
        DriverAccountEntity driverAccount = new DriverAccountEntity();
        driverAccount.setId(IdUtil.getSnowflakeNextId());
        driverAccount.setOpenid(openid);
        driverAccount.setNickname(dto.getNickname());
        driverAccount.setPhoto(dto.getPhoto());
        driverAccount.setStatus((byte) 0);
        driverAccount.setArchive(false);
        driverAccount.setCreateTime(LocalDateTime.now());
        driverAccount.setUpdateTime(LocalDateTime.now());
        driverAccountMapper.insert(driverAccount);

        // TODO 插入设置记录

        // TODO 插入钱包记录

        return String.valueOf(driverAccount.getId());
    }

    @Override
    public void authenticate(AuthenticateDTO dto) {
        DriverAccountEntity driverAccount = driverAccountMapper.selectOne(lambdaQuery(DriverAccountEntity.class)
                .eq(DriverAccountEntity::getId, dto.getDriverID()));
        Assert.isTrue(driverAccount.getStatus() == 0, E.AUTHENTICATED);

        BeanUtil.copyProperties(dto, driverAccount);
        driverAccount.setStatus((byte) 1);
        driverAccount.setArchive(false);
        driverAccount.setUpdateTime(LocalDateTime.now());
        driverAccountMapper.updateById(driverAccount);
    }

    @Override
    public void archive(String driverID, String image) {
        // 上传认证信息后才可以上传人脸存档
        DriverAccountEntity driverAccount = driverAccountMapper.selectOne(lambdaQuery(DriverAccountEntity.class)
                .select(DriverAccountEntity::getStatus, DriverAccountEntity::getName)
                .eq(DriverAccountEntity::getId, driverID));
        Assert.isTrue(driverAccount.getStatus() != 0, E.NOT_AUTHENTICATED);

        faceUtil.createFaceModel(driverID, driverAccount.getName(), image);
        driverAccountMapper.update(null, lambdaUpdate(DriverAccountEntity.class)
                .set(DriverAccountEntity::getArchive, true)
                .eq(DriverAccountEntity::getId, driverID));
    }

    @Override
    public LoginDTO login(String code) {
        // 确保司机已注册
        String openid = getOpenid(code);
        DriverAccountEntity driverAccount = driverAccountMapper.selectOne(lambdaQuery(DriverAccountEntity.class)
                .select(DriverAccountEntity::getId, DriverAccountEntity::getStatus, DriverAccountEntity::getArchive)
                .eq(DriverAccountEntity::getOpenid, openid));
        Assert.notNull(driverAccount, E.NOT_REGISTERED);

        LoginDTO dto = new LoginDTO();
        dto.setId(driverAccount.getId());
        dto.setStatus(driverAccount.getStatus());
        dto.setArchive(driverAccount.getArchive());
        return dto;
    }

    private String getOpenid(String code) {
        String response = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", Map.of(
                "appid", wxProperties.getAppid(),
                "secret", wxProperties.getSecret(),
                "js_code", code,
                "grant_type", "authorization_code"));
        JSONObject jsonObject = JSONUtil.parseObj(response);
        return jsonObject.getStr("openid");
//        return "code-openid";
    }
}
