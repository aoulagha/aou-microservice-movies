package com.amin.dev.role.error;

import com.amin.dev.role.error.RoleException;

public class ActingValidationException extends RoleException {
    public ActingValidationException(String name) {
        super(name);
    }
}
