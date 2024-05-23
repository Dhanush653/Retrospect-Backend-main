package com.example.retrospect.user.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity

@Table(name = "userTabel")
public class UserEntity {
    @Id
    @GeneratedValue
    @Column
    private int userId;
    @Column
    private String userName;
    @Column
    private String userEmail;
    @Column
    private String userPassword;
}
