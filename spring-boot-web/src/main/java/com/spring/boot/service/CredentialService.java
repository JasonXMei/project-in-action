package com.spring.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.boot.dto.PageReq;
import com.spring.boot.dto.PageResp;
import com.spring.boot.dto.TokenCreateReq;
import com.spring.boot.entity.Credential;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author Jason
 * @since 2022-06-14
 */
public interface CredentialService extends IService<Credential> {

    Credential getByParam(TokenCreateReq dto);

    Credential getByAppId(String appId);

    void verifyToken(String token);

    PageResp<Credential> getList(PageReq req);

}
