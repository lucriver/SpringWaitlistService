package com.lucrivdev.tapioca.controller;

import com.lucrivdev.tapioca.dto.ApiResponseDTO;
import com.lucrivdev.tapioca.dto.EmailRequestDTO;
import com.lucrivdev.tapioca.exception.InvalidEmailException;
import com.lucrivdev.tapioca.exception.DuplicateEmailException;
import com.lucrivdev.tapioca.service.EmailWaitlistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class WaitlistController {

    private final EmailWaitlistService service;

    public WaitlistController(EmailWaitlistService service) {
        this.service = service;
    }

    @PostMapping("/waitlist")
    public ResponseEntity<ApiResponseDTO> joinWaitList(@Valid @RequestBody EmailRequestDTO dto) {
        try {
            this.service.addEmailToWaitlist(dto.getEmail());
            return ResponseEntity.status(201).body(new ApiResponseDTO(201, "Email added successfully."));
        } catch (InvalidEmailException e) {
            System.out.println("Invalid argument: " + e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponseDTO(400, "Invalid email format."));
        } catch (DuplicateEmailException e) {
            System.out.println("Duplicate email: " + e.getMessage());
            return ResponseEntity.status(409).body(new ApiResponseDTO(409, "Email already on waitlist."));
        }
    }
}
