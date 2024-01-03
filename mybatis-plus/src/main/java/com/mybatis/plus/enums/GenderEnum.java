package com.mybatis.plus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum GenderEnum {

    MAN(0, "男"), WOMAN(1, "女");

    GenderEnum(int genderCode, String desc) {
        this.genderCode = genderCode;
        this.desc = desc;
    }

    @EnumValue
    private int genderCode;
    private String desc;

}
