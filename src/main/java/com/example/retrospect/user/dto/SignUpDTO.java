package com.example.retrospect.user.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SignUpDTO {


    private int userId;

    private String userName;

    private String userEmail;

    private String userPassword;
}
