package com.work.investitaly.service.impl;

import jakarta.servlet.ServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final UserServiceImpl userService;
    private final ServletRequest request;

    @Autowired
    public AuthenticationProviderImpl(UserServiceImpl userService, ServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = userService.loadUserByUsername(username);

        if (userDetails == null || !userDetails.getPassword().equals(password)) return null;

        log.info("Username: " + username + ", IP: " + request.getRemoteAddr());

        return new UsernamePasswordAuthenticationToken(
                username, password, userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
