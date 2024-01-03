package com.spring.boot.dto;

import com.spring.boot.validator.Create;
import com.spring.boot.validator.DateValidation;
import com.spring.boot.validator.Update;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author Jason
 * @Date 2022-06-21
 */
@Data
public class BeanValidationDTO {

    // 字符串不能为 null,字符串trim()后也不能等于 “"
    @NotBlank
    private String notBlankStr;

    // 不能为 null，可以是空
    @NotNull
    private String notNullStr;

    @Length(min = 1, max = 10)
    private String lengthStr;

    @AssertTrue
    private Boolean booleanTrue;

    // integer 整数位数，fraction 小数位数
    @Digits(integer = 4, fraction = 2)
    private BigDecimal number;

    @DecimalMax(value = "99.99")
    @DecimalMin(value = "0.0")
    private BigDecimal minAndMaxDecimal;

    @Min(10)
    @Max(15)
    private Long minAndMaxNumber;

    @Pattern(regexp =  "\\d+")
    private String patternNumber;

    @Size(max = 5)
    private List<Integer> size;

    @DateValidation(format = "yyyy-MM-dd", message = "date format should be yyyy-MM-dd", groups = Create.class)
    @NotNull(groups = Create.class)
    private String createDate;

    @DateValidation(format = "yyyy-MM-dd", message = "date format should be yyyy-MM-dd", groups = Update.class)
    @NotNull(groups = Update.class)
    private String updateDate;

}
