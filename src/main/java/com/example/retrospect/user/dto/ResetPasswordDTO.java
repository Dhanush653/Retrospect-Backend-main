package com.example.retrospect.user.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component

public class ResetPasswordDTO {
    private String userEmail;
    private String oldPassword;
    private String newPassword;

}
