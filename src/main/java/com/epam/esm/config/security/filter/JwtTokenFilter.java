package com.epam.esm.config.security.filter;

import com.epam.esm.config.security.filter.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private static final Logger LOG = Logger.getLogger(JwtTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String username;

        if (true){
            filterChain.doFilter(request, response);
            return;
        }

        if (checkEmptyToken(request, response, filterChain, authHeader)) return;
        if (checkGoogleLogin(request, response, filterChain, authHeader)) return;

        try {
            final String token = authHeader.split(" ")[1].trim();

            if (!jwtUtils.validateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }
            username = jwtUtils.getUsernameFromJwtToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.addHeader(HttpHeaders.AUTHORIZATION, JwtUtils.TOKEN_PREFIX + token);
        } catch (Exception e) {
            LOG.warn("Invalid token");
        }

    }

    private boolean checkGoogleLogin(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authHeader) throws IOException, ServletException {
        if (!isEmpty(authHeader) && request.getServletPath().equals("/users/google/login")) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

    private boolean checkEmptyToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authHeader) throws IOException, ServletException {
        if (isEmpty(authHeader) || !authHeader.startsWith(JwtUtils.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }
}