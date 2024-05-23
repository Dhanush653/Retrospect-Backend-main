package com.example.retrospect.user.service;

import com.example.retrospect.user.dto.LoginDTO;
import com.example.retrospect.user.dto.ResetPasswordDTO;
import com.example.retrospect.user.dto.SignUpDTO;
import com.example.retrospect.user.dto.UpdateUserDTO;
import com.example.retrospect.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface IUserService {
    String Userlogin(LoginDTO loginDTO);

    String userSignup(SignUpDTO signUpDTO);

    Optional<UserEntity> getUserByJWT(String token);

    UserEntity updateUser(int id, UpdateUserDTO userEntity);

    String resetPassword(String userEmail, String oldPassword, String newPassword);

    String forgotPassword(String email);

    String changePassword(String email, String otp, String newPassword);

    public UserEntity getEmail(String email);

    UserEntity createUser(UserEntity userEntity);
}
