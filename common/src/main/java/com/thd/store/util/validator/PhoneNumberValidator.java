package com.thd.store.util.validator;


import com.thd.store.util.ConstUtil;
import com.thd.store.util.anotation.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.hasText(value)) {
            return Pattern.compile(ConstUtil.REGEX_PHONE_NUMBER).matcher(value).matches();
        }else{
            return true;
        }
    }
}
