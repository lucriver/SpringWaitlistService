package com.lucrivdev.tapioca.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequestDTO {

    @NotBlank(message = "Email cannot be empty or null.")
    private String email;
}
