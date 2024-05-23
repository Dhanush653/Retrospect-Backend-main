package com.example.retrospect.user.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginDTO {
    private String userEmail;
    private String userPassword;

}
