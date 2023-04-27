package com.epam.esm.service;

import com.epam.esm.config.security.filter.util.JwtUtils;
import com.epam.esm.dto.user.UserLoginRequestModel;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public ResponseEntity<Object> login(UserLoginRequestModel loginRequestModel) {
        try {
            String email = loginRequestModel.getEmail();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            loginRequestModel.getPassword()
                    )
            );
            User user = userRepository.findByEmail(email).
                    orElseThrow(() -> new ResourceNotFoundException("User with email: " + email + " not found"));
            System.out.println(user.getRole());
            String token = jwtUtils.createToken(authentication, user.getRole());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(headers).build();
        } catch (AuthenticationException | ResourceNotFoundException exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
