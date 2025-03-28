package com.lucrivdev.tapioca.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Email already on waitlist: " + email);
    }
}
