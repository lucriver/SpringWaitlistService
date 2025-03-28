package com.lucrivdev.tapioca.service;

import com.lucrivdev.tapioca.exception.DuplicateEmailException;
import com.lucrivdev.tapioca.exception.InvalidEmailException;
import com.lucrivdev.tapioca.model.WaitlistEntry;
import com.lucrivdev.tapioca.repository.WaitlistRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailWaitlistServiceTest {
    @Test
    void shouldSaveEmailIfNotExists() {
        WaitlistRepository mockRepo = mock(WaitlistRepository.class);
        when(mockRepo.findByEmail("user@example.com")).thenReturn(Optional.empty());

        EmailWaitlistService service = new EmailWaitlistService(mockRepo);

        assertDoesNotThrow(() -> {
            service.addEmailToWaitlist("user@example.com");
        });

        verify(mockRepo).save(any(WaitlistEntry.class));
    }

    @Test
    void shouldThrowIfEmailAlreadyExists() {
        WaitlistRepository mockRepo = mock(WaitlistRepository.class);
        when(mockRepo.findByEmail("user@example.com")).thenReturn(Optional.of(new WaitlistEntry("user@example.com")));

        EmailWaitlistService service = new EmailWaitlistService(mockRepo);

        assertThrows(DuplicateEmailException.class, () -> {
            service.addEmailToWaitlist("user@example.com");
        });
    }

    @Test
    void shouldThrowIfEmailMissingOrNull() {
        WaitlistRepository mockRepo = mock(WaitlistRepository.class);

        EmailWaitlistService service = new EmailWaitlistService(mockRepo);

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
        WaitlistRepository mocKRepo = mock(WaitlistRepository.class);

        EmailWaitlistService service = new EmailWaitlistService(mocKRepo);

        assertThrows(InvalidEmailException.class, () -> {
            service.addEmailToWaitlist("user");
        });
    }

    @Test
    void shouldThrowIfEmailExceedsMaxLength() {
        WaitlistRepository mockRepo = mock(WaitlistRepository.class);
        String local = "a".repeat(64);
        String domain = "b".repeat(186) + ".com";
        String email = local + "@" + domain;

        EmailWaitlistService service = new EmailWaitlistService(mockRepo);
        assertThrows(InvalidEmailException.class, () -> {
            service.addEmailToWaitlist(email);
        });
    }

}
