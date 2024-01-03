package com.spring.boot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.spring.boot.dto.BaseResp;
import com.spring.boot.dto.TokenCreateReq;
import com.spring.boot.dto.TokenCreateResp;
import com.spring.boot.dto.TokenPayload;
import com.spring.boot.entity.Credential;
import com.spring.boot.service.CredentialService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Jason
 * @since 2022-06-14
 */
@Api(tags = "Token APIs")
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private CredentialService credentialService;

    @Operation(summary = "Generate Token")
    @PostMapping
    public BaseResp<TokenCreateResp> createToken(@Valid TokenCreateReq dto) {
        Credential record = credentialService.getByParam(dto);

        JWTSigner jwtSigner = JWTSignerUtil.createSigner(record.getAlgorithm().name(), record.getSigner().getBytes());
        Map<String, Object> padload = new HashMap<String, Object>() {
            {
                put(JWTPayload.ISSUED_AT, DateUtil.currentSeconds()); // 签发时间
                put(JWTPayload.EXPIRES_AT, DateUtil.currentSeconds() + record.getExpiredAt()); // 失效时间

                TokenPayload payload = new TokenPayload();
                payload.setAppId(record.getAppId());
                put(JWTPayload.SUBJECT, JSONUtil.toJsonStr(payload));
            }
        };

        String token = JWTUtil.createToken(padload, jwtSigner);
        TokenCreateResp resp = TokenCreateResp.builder()
                .token(token)
                .issuedAt(DateUtil.currentSeconds())
                .expiredAt(DateUtil.currentSeconds() + record.getExpiredAt())
                .build();
        return BaseResp.success(resp);
    }

    @Operation(summary = "Verify Token")
    @PostMapping("/verify")
    public BaseResp<Boolean> verifyToken(String token) {
        credentialService.verifyToken(token);
        return BaseResp.success(Boolean.TRUE);
    }

}
