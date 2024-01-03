package com.spring.boot.validator;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Author Jason
 * @Date 2022-06-21
 */
public class DateValidator implements ConstraintValidator<DateValidation, String> {

    private String format;

    @Override
    public void initialize(DateValidation dateValidation) {
        this.format = dateValidation.format();
    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isEmpty(dateStr)) {
            return true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
