package com.lucrivdev.tapioca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmailRequestDTO {

    @NotBlank(message = "Email cannot be empty or null.")
    @Email(message = "Invalid email address.")
    @Pattern(regexp = "^[\\x00-\\x7F]+$", message = "Email contains non-ASCII characters")
    private String email;
}
