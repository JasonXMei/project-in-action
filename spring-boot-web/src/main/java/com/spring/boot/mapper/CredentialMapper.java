package com.spring.boot.mapper;

import com.spring.boot.dto.TokenCreateReq;
import com.spring.boot.entity.Credential;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Author Jason
 * @since 2022-06-14
 */
public interface CredentialMapper extends BaseMapper<Credential> {

    Credential getByParam(TokenCreateReq dto);

}
