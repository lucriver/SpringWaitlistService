package com.lucrivdev.tapioca.service;

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
            throw new IllegalArgumentException("Invalid email");
        }

        if (this.repository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already on waitlist");
        }

        repository.save(new WaitlistEntry(email));
    }

    private boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

}
