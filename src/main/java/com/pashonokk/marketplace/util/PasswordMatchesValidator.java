package com.pashonokk.marketplace.util;

import com.pashonokk.marketplace.annotation.PasswordMatches;
import com.pashonokk.marketplace.dto.PasswordChangingDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordChangingDto> {
    @Override
    public boolean isValid(PasswordChangingDto dto, ConstraintValidatorContext context) {
        return dto.getNewPassword().equals(dto.getNewPasswordConfirmation());
    }
}
