/*package com.credit.credit.service.login;

import com.credit.credit.error.NotFoundObject;
import com.credit.credit.model.Customer;
import com.credit.credit.model.UserLoginRequest;
import com.credit.credit.repository.CustomerRepository;
import com.credit.credit.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;

    @Override
    public String login(UserLoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginRequest.getTckn(), loginRequest.getPassword()));
        return JwtTokenUtil.generateToken(getUserDetailsByTckn(loginRequest.getTckn(), loginRequest.getPassword()));
    }

    private UserDetails getUserDetailsByTckn(String tckn, String password) {
        Customer customer = customerRepository.findByTckn(tckn).orElseThrow((() -> new NotFoundObject("Customer Not Found")));
        return new User(customer.getTckn(), password, new ArrayList<>());
    }
}
*/