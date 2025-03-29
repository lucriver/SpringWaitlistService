package com.lucrivdev.tapioca.service;

import com.lucrivdev.tapioca.exception.DuplicateEmailException;
import com.lucrivdev.tapioca.exception.InvalidEmailException;
import com.lucrivdev.tapioca.model.WaitlistEntry;
import com.lucrivdev.tapioca.repository.WaitlistRepository;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;


@Service
public class EmailWaitlistService {
    private final WaitlistRepository repository;

    public EmailWaitlistService(WaitlistRepository repository) {
        this.repository = repository;
    }

    public void addEmailToWaitlist(String email) {
        if (!isValidEmail(email)) {
            throw new InvalidEmailException("Email is invalid: " + email);
        }

        if (this.repository.findByEmail(email).isPresent()) {
            throw new DuplicateEmailException(email);
        }

        repository.save(new WaitlistEntry(email));
    }

    private boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

}
