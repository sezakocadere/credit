package com.credit.credit.service.login;

import com.credit.credit.error.NotFoundObject;
import com.credit.credit.model.Customer;
import com.credit.credit.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String tckn) throws UsernameNotFoundException {
        return getUserDetailsByTckn(tckn);
    }

    private UserDetails getUserDetailsByTckn(String tckn) {
        return new User(tckn, getByTckn(tckn).getTckn(), new ArrayList<>());
    }

    private Customer getByTckn(String tckn) {
        return customerRepository.findByTckn(tckn).orElseThrow(() -> new NotFoundObject("Customer Not Found"));
    }
}