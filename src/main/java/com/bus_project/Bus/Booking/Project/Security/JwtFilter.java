package com.bus_project.Bus.Booking.Project.Security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final Jwt jwt;

    public JwtFilter(Jwt jwt) {
        this.jwt = jwt;
    }

    //doFilterInternal intercepts every request before it reaches the controller
    //It extracts the Authorization header from the request, and the token
    //It validates the token, and if valid it extracts the user email
    //It creates a UserDetails object, and sets it as authenticated
    //It then passes the request to the next filter/controller
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
        throws ServletException, IOException {

            final String authHeader = request.getHeader("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }
        

        String token = authHeader.substring(7);
        String email = jwt.validateTokenAndGetEmail(token);

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = User.withUsername(email).password("").authorities("USER").build();
            SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                )
            );
        }

        chain.doFilter(request, response);
    }
}

