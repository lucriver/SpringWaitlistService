package com.lucrivdev.tapioca.service;

import com.lucrivdev.tapioca.exception.DuplicateEmailException;
import com.lucrivdev.tapioca.exception.InvalidEmailException;
import com.lucrivdev.tapioca.model.WaitlistEntry;
import com.lucrivdev.tapioca.repository.WaitlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailWaitlistServiceTest {
    @Mock
    private WaitlistRepository repository;

    @InjectMocks
    private EmailWaitlistService service;

    @Test
    void shouldSaveEmailIfNotExists() {
        when(repository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            service.addEmailToWaitlist("user@example.com");
        });

        verify(repository).save(any(WaitlistEntry.class));
    }

    @Test
    void shouldThrowIfEmailAlreadyExists() {
        when(repository.findByEmail("user@example.com")).thenReturn(Optional.of(new WaitlistEntry("user@example.com")));

        assertThrows(DuplicateEmailException.class, () -> {
            service.addEmailToWaitlist("user@example.com");
        });
    }

    @Test
    void shouldThrowIfEmailMissingOrNull() {
        // Empty string
        assertThrows(InvalidEmailException.class, () -> {
            service.addEmailToWaitlist("");
        });

        // null argument
        assertThrows(InvalidEmailException.class, () -> {
            service.addEmailToWaitlist(null);
        });
    }

    @Test
    void shouldThrowIfEmailIncomplete() {
        assertThrows(InvalidEmailException.class, () -> {
            service.addEmailToWaitlist("user");
        });
    }

    @Test
    void shouldThrowIfEmailExceedsMaxLength() {
        String local = "a".repeat(64);
        String domain = "b".repeat(186) + ".com";
        String email = local + "@" + domain;

        assertThrows(InvalidEmailException.class, () -> {
            service.addEmailToWaitlist(email);
        });
    }

}
