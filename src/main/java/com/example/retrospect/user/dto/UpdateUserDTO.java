package com.example.retrospect.user.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UpdateUserDTO {
    private String userName;
    private String userEmail;
}
