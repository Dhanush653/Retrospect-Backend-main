package com.example.retrospect.user.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ChangePasswordDTO {
    private String userEmail;
    private String userOtp;
    private String userNewPassword;
}
