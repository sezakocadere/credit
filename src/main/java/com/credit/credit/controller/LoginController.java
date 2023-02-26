package com.credit.credit.controller;

import com.credit.credit.model.JwtResponse;
import com.credit.credit.model.UserLoginRequest;
import com.credit.credit.service.login.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
@RequiredArgsConstructor

public class LoginController {
    private final UserLoginService userLoginService;

    @PostMapping
    public JwtResponse login(@RequestBody UserLoginRequest loginRequest) {
        return new JwtResponse(userLoginService.login(loginRequest));
    }
}
