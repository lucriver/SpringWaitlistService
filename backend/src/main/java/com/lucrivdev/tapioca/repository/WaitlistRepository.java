package com.lucrivdev.tapioca.repository;

import com.lucrivdev.tapioca.model.WaitlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaitlistRepository extends JpaRepository<WaitlistEntry, Long> {
    Optional<WaitlistEntry> findByEmail(String email);
}
