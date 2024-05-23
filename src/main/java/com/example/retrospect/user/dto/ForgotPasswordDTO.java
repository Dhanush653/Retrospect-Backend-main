package com.example.retrospect.user.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ForgotPasswordDTO {
    private String userEmail;
}
