package com.example.retrospect.user.service;

import com.example.retrospect.user.dto.LoginDTO;
import com.example.retrospect.user.dto.SignUpDTO;
import com.example.retrospect.user.dto.UpdateUserDTO;
import com.example.retrospect.user.entity.UserEntity;
import com.example.retrospect.user.repository.IUserRepository;
import com.example.retrospect.util.ForgotPassword;
import com.example.retrospect.util.UserJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserService implements IUserService {
  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    IUserRepository iUserRepository;


    @Autowired
    UserJWT userJWT;

    @Autowired
    ForgotPassword forgot;

    @Override
    public String userSignup(SignUpDTO signUpDTO) {
        UserEntity userEntity = new UserEntity();
        String encryptedPassword=bCryptPasswordEncoder.encode(signUpDTO.getUserPassword());
        signUpDTO.setUserPassword((encryptedPassword));

        userEntity.setUserEmail(signUpDTO.getUserEmail());
        userEntity.setUserName(signUpDTO.getUserName());
        userEntity.setUserPassword(signUpDTO.getUserPassword());
        iUserRepository.save(userEntity);

        return  "You have been signed up successfully";

    }

    @Override
    public Optional<UserEntity> getUserByJWT(String token) {
        int userId = userJWT.decodeToken(token);
        System.out.println(" service" + userId);
        return iUserRepository.findById(userId);
    }


    @Override
    public String Userlogin(LoginDTO loginDTO) {
        UserEntity userEntity = iUserRepository.findByEmailId(loginDTO.getUserEmail());
        if(userEntity != null && bCryptPasswordEncoder.matches(loginDTO.getUserPassword(), userEntity.getUserPassword())) {
            String token = userJWT.generateToken(userEntity.getUserId());
            return token;
        }
        return "login failed";
    }

    @Override
    public UserEntity updateUser(int id, UpdateUserDTO userEntity){
        Optional<UserEntity> userOptional = iUserRepository.findById(id);
        if(userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setUserName(userEntity.getUserName());
            user.setUserEmail(userEntity.getUserEmail());
            return iUserRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public String resetPassword(String userEmail, String oldPassword, String newPassword) {
        UserEntity user = iUserRepository.findByEmailId(userEmail); // Using userEmail parameter
        if (user != null && bCryptPasswordEncoder.matches(oldPassword, user.getUserPassword())) { // Using oldPassword parameter
            // Encode the new password
            String newPasswordHash = bCryptPasswordEncoder.encode(newPassword); // Using newPassword parameter

            // Set new encoded password
            user.setUserPassword(newPasswordHash);

            // Save the updated user
            iUserRepository.save(user);

            // Return success message or updated user details
            return "Password reset successful";
        } else {
            // Return error message or handle as needed
            return "Invalid email or old password";
        }
    }
    @Override
    public String forgotPassword(String email) {
        String otp = forgot.generateOtp();
        forgot.sendOtp(email,otp);
        return "OTP sent to " + email;
    }

    @Override
    public String changePassword(String email, String otp, String newPassword) {
        forgot.changePassword(email,otp,newPassword);

        return "Password changed successfully";
    }

    @Override
    public UserEntity getEmail(String email) {
        return iUserRepository.findByEmailId(email);
    }
    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return iUserRepository.save(userEntity);
    }

}
