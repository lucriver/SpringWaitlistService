package com.lucrivdev.tapioca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ApiResponseDTO {
    private int status;
    private String message;
}
