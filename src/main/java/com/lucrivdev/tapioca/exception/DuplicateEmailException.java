package com.lucrivdev.tapioca.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateEmailException extends DataIntegrityViolationException {
    public DuplicateEmailException(String email) {
        super("Email already on waitlist: " + email);
    }
}
