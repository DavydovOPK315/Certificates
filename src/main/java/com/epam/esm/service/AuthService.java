package com.epam.esm.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.esm.config.security.filter.util.JwtUtils;
import com.epam.esm.dto.user.UserLoginRequestModel;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private static final Logger LOG = Logger.getLogger(AuthService.class);

    public ResponseEntity<Object> login(UserLoginRequestModel loginRequestModel) {
        try {
            String email = loginRequestModel.getEmail();
            String password = loginRequestModel.getPassword();
            User user = userRepository.findByEmail(email).
                    orElseThrow(() -> new ResourceNotFoundException("User with email: " + email + " not found"));
            return provideAuth(email, password, user);
        } catch (AuthenticationException | ResourceNotFoundException exception) {
            LOG.error("Unable to authenticate with loginRequestModel");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<Object> googleOauth(String googleAuthToken) {
        try {
            final String clearGoogleToken = googleAuthToken.split(" ")[1].trim();
            DecodedJWT jwt = JWT.decode(clearGoogleToken);
            Date tokenExpiration = jwt.getClaim("exp").asDate();

            if (checkExpiration(tokenExpiration)) {
                throw new AccountExpiredException("Account has expired");
            }
            String email = jwt.getClaim("email").asString();
            User user = userRepository.findByEmail(email).
                    orElseThrow(() -> new ResourceNotFoundException("User with email: " + email + " not found"));
            String password = user.getPassword();
            return provideAuth(email, password, user);
        } catch (AuthenticationException | ResourceNotFoundException exception) {
            LOG.error("Unable to authenticate with googleAuthToken");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private ResponseEntity<Object> provideAuth(String email, String password, User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
        String token = jwtUtils.createToken(authentication, user.getRole());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, JwtUtils.TOKEN_PREFIX + token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LOG.info("Successfully authenticated");
        return ResponseEntity.ok().headers(headers).build();
    }

    private boolean checkExpiration(Date tokenExpiration) {
        return tokenExpiration.before(new Date());
    }
}