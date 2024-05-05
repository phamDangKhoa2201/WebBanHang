package com.project.shopaap.controllers;

import com.project.shopaap.dtos.*;
import com.project.shopaap.respones.LoginRespone;
import com.project.shopaap.respones.RegisterRespone;
import com.project.shopaap.services.IUserService;
import com.project.shopaap.components.LocalizationUtil;
import com.project.shopaap.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UsersController {
    private final IUserService userService;
    private final LocalizationUtil localizationUtil;
    @PostMapping("/register")
    public ResponseEntity<RegisterRespone> createUser(@Valid @RequestBody UserDTO userDTO,
                                                      BindingResult result) throws Exception
    {
        try {
            if (result.hasErrors()) {

                List<String> errorMessage = result.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(
                        RegisterRespone.builder()
                                .message(localizationUtil.getLocalizedMessage(MessageKeys.REGISTER_FAILED,errorMessage))
                                .build()
                );
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body(RegisterRespone.builder()
                                .message(localizationUtil.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH))
                        .build());
            }
            return ResponseEntity.ok( RegisterRespone.builder()
                            .message(localizationUtil.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY))
                            .user(userService.createUser(userDTO))
                    .build());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(RegisterRespone.builder()
                    .message(localizationUtil.getLocalizedMessage(MessageKeys.REGISTER_FAILED,e.getMessage()))
                    .build());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginRespone> login(@Valid @RequestBody UserLoginDTO userLoginDTODTO
                                              ){
        try {
           String token = userService.login(userLoginDTODTO.getPhoneNumber(),userLoginDTODTO.getPassword());
            return ResponseEntity.ok(LoginRespone.builder()
                            .message(localizationUtil.getLocalizedMessage
                                    (MessageKeys.LOGIN_SUCCESSFULLY))
                            .token(token)
                    .build());
        }
        catch (Exception e ){
            return ResponseEntity.badRequest().body(LoginRespone.builder()
                    .message(localizationUtil.getLocalizedMessage(MessageKeys.LOGIN_FAILED,e.getMessage()))
                    .build());
        }

    }

}
