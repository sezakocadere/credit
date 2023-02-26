package com.credit.credit.service.login;

import com.credit.credit.model.UserLoginRequest;

import javax.validation.Valid;

public interface UserLoginService {
    String login(@Valid UserLoginRequest loginRequest);
}
