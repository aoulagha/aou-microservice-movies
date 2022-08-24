package com.amin.dev.role;

import com.amin.dev.role.combinator.ValidationResult;
import com.amin.dev.role.error.RoleException;

public class DirectingValidationException extends RoleException {

    public DirectingValidationException(String message) {
        super(message);
    }
}
