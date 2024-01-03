package com.spring.boot.controller;

import com.spring.boot.dto.BeanValidationDTO;
import com.spring.boot.validator.Update;
import com.spring.boot.validator.ValidateList;
import com.spring.boot.validator.Create;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author Jason
 * @since 2022-06-09
 */
@Api(tags = "BeanValid Demo")
@RestController
@RequestMapping("/valid")
public class BeanValidController {

    @Operation(summary = "Body Create")
    @PostMapping("/body/create")
    public BeanValidationDTO validCreate(@RequestBody @Validated(Create.class) BeanValidationDTO dto) {
        return dto;
    }

    @Operation(summary = "Body Update")
    @PostMapping("/body/update")
    public BeanValidationDTO validUpdate(@RequestBody @Validated(Update.class) BeanValidationDTO dto) {
        return dto;
    }

    @Operation(summary = "Form")
    @PostMapping("/form")
    public BeanValidationDTO validForm(@Validated BeanValidationDTO dto) {
        return dto;
    }

    @Operation(summary = "Validate List")
    @PostMapping("/list")
    public List<BeanValidationDTO> validList(@RequestBody @Validated ValidateList<BeanValidationDTO> list) {
        return list;
    }

}
