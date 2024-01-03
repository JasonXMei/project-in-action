package com.spring.boot.controller;

import com.spring.boot.dto.*;
import com.spring.boot.entity.Credential;
import com.spring.boot.enums.ResponseCodeEnum;
import com.spring.boot.exception.BusinessException;
import com.spring.boot.service.CredentialService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author Jason
 * @since 2023-04-26
 */
@Api(tags = "Credential APIs")
@RestController
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Operation(summary = "Create")
    @PostMapping("/create")
    public BaseResp<CredentialCreateResp> create(@RequestBody @Validated CredentialCreateReq req) {
        Credential credential = new Credential();
        BeanUtils.copyProperties(req, credential);
        credentialService.save(credential);

        CredentialCreateResp resp = CredentialCreateResp.builder()
                .id(credential.getId())
                .build();
        return BaseResp.success(resp);
    }

    @Operation(summary = "Update")
    @PutMapping("/update")
    public BaseResp<Void> update(CredentialCreateReq req) {
        Credential credential = credentialService.getByAppId(req.getAppId());
        if (Objects.isNull(credential)) {
            throw new BusinessException(ResponseCodeEnum.BAD_REQUEST, "not found relate record");
        }

        Credential credentialUpdate = new Credential();
        BeanUtils.copyProperties(req, credentialUpdate);
        credentialUpdate.setId(credential.getId());
        credentialUpdate.setVersion(credential.getVersion());
        credentialService.updateById(credentialUpdate);
        return BaseResp.success();
    }

    @Operation(summary = "Delete")
    @DeleteMapping("/delete")
    public BaseResp<Void> delete(@RequestParam("id") Long id) {
        credentialService.removeById(id);
        return BaseResp.success();
    }

    @Operation(summary = "Get")
    @GetMapping("/get")
    public BaseResp<Credential> get(@RequestParam("appId") String appId) {
        Credential credential = credentialService.getByAppId(appId);
        return BaseResp.success(credential);
    }

    @Operation(summary = "List")
    @GetMapping("/list")
    public BaseResp<PageResp<Credential>> list(@Validated PageReq req) {
        PageResp<Credential> list = credentialService.getList(req);
        return BaseResp.success(list);
    }

}
