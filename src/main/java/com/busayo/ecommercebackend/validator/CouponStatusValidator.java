package com.busayo.ecommercebackend.validator;

import com.busayo.ecommercebackend.annotation.CouponStatusAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


public class CouponStatusValidator implements ConstraintValidator<CouponStatusAnnotation, CharSequence> {

    private List<String> acceptedValues = new ArrayList<>();

    @Override
    public void initialize(CouponStatusAnnotation annotation) {
        for (Enum<?> enumValue : annotation.enumClass().getEnumConstants()) {
            acceptedValues.add(enumValue.toString());
        }
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return acceptedValues.contains(value.toString().toUpperCase());
    }
}
