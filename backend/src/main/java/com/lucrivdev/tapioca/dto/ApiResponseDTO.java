package com.lucrivdev.tapioca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ApiResponseDTO {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ApiResponseDTO(int status, String message) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
