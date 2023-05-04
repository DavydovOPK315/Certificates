package com.epam.esm.config.security;

import com.epam.esm.config.security.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtTokenFilter jwtTokenFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setContextPath("/api/v1");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomBCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests(authorizeConfig -> {
                    authorizeConfig.antMatchers(HttpMethod.GET, "/certificates/**").permitAll();
                    authorizeConfig.antMatchers("/users/register").permitAll();
                    authorizeConfig.antMatchers("/users/login").permitAll();
                    authorizeConfig.antMatchers("/users/google/login").permitAll();
                    authorizeConfig.antMatchers(HttpMethod.GET, "/tags/**").hasAnyRole("USER", "ADMIN");
                    authorizeConfig.antMatchers(HttpMethod.GET, "/users/**").hasAnyRole("USER", "ADMIN");
                    authorizeConfig.antMatchers("/orders/**").hasAnyRole("USER", "ADMIN");
                    authorizeConfig.antMatchers("/**").hasRole("ADMIN");
                    authorizeConfig.anyRequest().authenticated();
                })
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .formLogin().disable()
                .build();
    }
}
