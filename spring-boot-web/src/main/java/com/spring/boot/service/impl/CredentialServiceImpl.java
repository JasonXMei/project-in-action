package com.spring.boot.service.impl;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.*;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.boot.dto.PageReq;
import com.spring.boot.dto.PageResp;
import com.spring.boot.dto.TokenCreateReq;
import com.spring.boot.dto.TokenPayload;
import com.spring.boot.enums.ResponseCodeEnum;
import com.spring.boot.exception.BusinessException;
import com.spring.boot.entity.Credential;
import com.spring.boot.mapper.CredentialMapper;
import com.spring.boot.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @Author Jason
 * @since 2022-06-14
 */
@Service
public class CredentialServiceImpl extends ServiceImpl<CredentialMapper, Credential> implements CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Override
    public Credential getByParam(TokenCreateReq dto) {
        Credential credential = credentialMapper.getByParam(dto);
        if (Objects.isNull(credential)) {
            throw new BusinessException(ResponseCodeEnum.BAD_REQUEST, "not found relate record");
        }

        return credential;
    }

    @Override
    public Credential getByAppId(String appId) {
        Credential credential = getOne(Wrappers.lambdaQuery(Credential.class).eq(Credential::getAppId, appId), false);
        if (Objects.isNull(credential)) {
            throw new BusinessException(ResponseCodeEnum.BAD_REQUEST, "not found relate record");
        }

        return credential;
    }

    @Override
    public void verifyToken(String token) {
        try {
            JWTValidator.of(token).validateDate();
        } catch (ValidateException validateException) {
            throw new BusinessException(ResponseCodeEnum.BAD_REQUEST, "token expired");
        } catch (JWTException jwtException) {
            throw new BusinessException(ResponseCodeEnum.BAD_REQUEST, jwtException.getMessage());
        } catch (Exception jsonException) {
            throw new BusinessException(ResponseCodeEnum.BAD_REQUEST, "token format illegal");
        }

        JWT jwt = JWTUtil.parseToken(token);

        Object sub = jwt.getPayload(JWTPayload.SUBJECT);
        TokenPayload payload = JSONUtil.toBean(JSONUtil.toJsonStr(sub), TokenPayload.class);

        Credential record = getByAppId(payload.getAppId());

        JWTSigner jwtSigner = JWTSignerUtil.createSigner(record.getAlgorithm().name(), record.getSigner().getBytes());

        if (!JWTUtil.verify(token, jwtSigner)) {
            throw new BusinessException(ResponseCodeEnum.BAD_REQUEST, "token verify failed");
        }
    }

    @Override
    public PageResp<Credential> getList(PageReq req) {
        Page<Credential> page = new Page<>(req.getCurrentPage(), req.getPageSize());
        IPage<Credential> pageResult = credentialMapper.selectPage(page, Wrappers.emptyWrapper());
        return PageResp.<Credential>builder()
                .list(pageResult.getRecords())
                .currentPage(pageResult.getCurrent())
                .pageNumber(pageResult.getPages())
                .pageSize(pageResult.getSize())
                .total(pageResult.getTotal())
                .build();
    }

}
