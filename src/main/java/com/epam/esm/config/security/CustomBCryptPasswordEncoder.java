package com.epam.esm.config.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomBCryptPasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword.equals(encodedPassword)) {
            return true;
        }
        return super.matches(rawPassword, encodedPassword);
    }
}
