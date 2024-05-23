package com.example.retrospect.util;

import com.example.retrospect.user.entity.UserEntity;
import com.example.retrospect.user.exceptions.UserException;
import com.example.retrospect.user.repository.IUserRepository;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class ForgotPassword {
    @Autowired
    private EmailSender emailsender;
    @Autowired
    private IUserRepository user;

    @Autowired
    private BCryptPasswordEncoder passwordconfig;

    private Map<String, String> otpmap = new HashMap<>();

    public String generateOtp(){
        Random random = new Random();
        int otp =random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtp(String email, String otp){
        String subject = "Mail for Reset password";
        String body = "Your otp for reset your password is " + otp + " Please click the link to enter the otp http://localhost:8080/user/change";

        emailsender.sendMail(email,subject,body);
        otpmap.put(email, otp);
    }

    public void changePassword(String email, String otp, String newPassword){
        if(verifyOtp(email, otp)){
            UserEntity userEntity = user.findByEmailId(email);
            String encodedPassword = passwordconfig.encode(newPassword);
            if(userEntity != null){
//                userEntity.setUser_password(newPassword);
                userEntity.setUserPassword(encodedPassword);
                user.save(userEntity);
                System.out.println("Password changed Successfully for " + email);
            }
            else{
                throw new UserException("User not found");
            }
        }
    }
    boolean verifyOtp(String email, String otp){
        String storedOtp = otpmap.get(email);

        return storedOtp != null && storedOtp.equals(otp);
    }
}
