package com.tiv.spring.ai.doctor.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SSEMessage {

    @NotBlank
    private String userName;

    @NotBlank
    private String message;

}
