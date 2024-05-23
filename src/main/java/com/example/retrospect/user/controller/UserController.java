package com.example.retrospect.user.controller;

import com.example.retrospect.user.dto.*;
import com.example.retrospect.user.entity.UserEntity;
import com.example.retrospect.user.repository.IUserRepository;
import com.example.retrospect.user.service.IUserService;
import com.example.retrospect.user.service.UserService;
import com.example.retrospect.util.UserJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@ResponseStatus(HttpStatus.OK)
@CrossOrigin(allowedHeaders = "*")
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    UserJWT userJWT;
    @PostMapping("/signup")
    public String userSignup(@RequestBody SignUpDTO signUpDTO) {
        return service.userSignup(signUpDTO);

    }
    @PostMapping("/login")
    public String UserLogin(@RequestBody LoginDTO loginDTO) {
        return service.Userlogin(loginDTO);

    }
    @GetMapping("/getbyJWT")
    public Optional<UserEntity> getUserByJWT(@RequestHeader String token) {
        return service.getUserByJWT(token);
    }

    @PutMapping("/update/{id}")
    public UserEntity updateUser (@PathVariable int id, @RequestBody UpdateUserDTO userEntity) {
        return service.updateUser(id,userEntity);
    }

    @PostMapping("/resetpassword")
    public String resetPassword (@RequestBody ResetPasswordDTO resetPassword) {
        return service.resetPassword(resetPassword.getUserEmail(), resetPassword.getOldPassword(), resetPassword.getNewPassword());

    }

    @PostMapping("/forgot")
    String forgetPassword(@RequestBody ForgotPasswordDTO request){
        return service.forgotPassword(request.getUserEmail());
    }
    @PostMapping("/change")
    String changePassword(@RequestBody ChangePasswordDTO change){
        return service.changePassword(change.getUserEmail(),change.getUserOtp(),change.getUserNewPassword());
    }

    @PostMapping("/check")
    public HashMap<String, String> checkUser(@RequestBody UserEntity userEntity) {
        HashMap<String, String> map = new HashMap<>();
        UserEntity existingUser = service.getEmail(userEntity.getUserEmail());

        if (existingUser != null) {
            map.put("message", "user already exists");
            map.put("email", existingUser.getUserEmail());
            map.put("userId", String.valueOf(existingUser.getUserId()));
            map.put("userName", existingUser.getUserName());
        } else {
            service.createUser(userEntity);
            map.put("message", "user created");
            map.put("email", userEntity.getUserEmail());
            map.put("userId", String.valueOf(userEntity.getUserId())); // Assuming userId is generated and set in userEntity during creation
            map.put("userName", userEntity.getUserEmail());
        }

        return map;
    }


//    @PostMapping("/create")
//    public HashMap<String ,String> createUser(@RequestBody UserEntity userEntity) {
//        service.createUser(userEntity);
//        HashMap<String,String> map = new HashMap<>();
//        map.put("message", "user created");
//        map.put("userId", String.valueOf(userEntity.getUserId()));
//        map.put("userName", userEntity.getUserName());
//        map.put("email", userEntity.getUserEmail());
//
//        return map;
//    }

}
