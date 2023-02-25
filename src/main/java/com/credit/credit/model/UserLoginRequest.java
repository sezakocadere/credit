package com.credit.credit.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserLoginRequest {
    @NotNull
    private String tckn;
    @NotNull
    private String password;
}
