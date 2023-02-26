package com.credit.credit.service.login;

import com.credit.credit.enums.Status;
import com.credit.credit.model.UserLoginRequest;
import com.credit.credit.service.customer.CustomerService;
import com.credit.credit.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {
    private final AuthenticationManager authenticationManager;

    private final CustomerService customerService;

    @Override
    public String login(UserLoginRequest loginRequest) {
        UserDetails userDetails = getUserDetailsByTckn(loginRequest.getTckn());
        return JwtTokenUtil.generateToken(userDetails);
    }

    private UserDetails getUserDetailsByTckn(String tckn) {
        return new User(tckn, customerService.getCustomerByTcknAndStatus(tckn, Status.ACTIVE).getName(), new ArrayList<>());
    }
}