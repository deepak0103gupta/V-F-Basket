package com.V.FBasket.VnFBasket.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(@Lazy UserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("========== JWT Filter Start ==========");
        System.out.println("Request URI: " + request.getRequestURI());

        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorizationHeader);

        String username = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            System.out.println("JWT Token extracted: " + jwt.substring(0, Math.min(20, jwt.length())) + "...");
            try{
                username = jwtUtil.extractUsername(jwt);
                System.out.println("Username extracted: " + username);
            } catch (Exception e){
                System.out.println("Error extracting username: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No Bearer token found in Authorization header");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Attempting to authenticate user: " + username);
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("UserDetails loaded: " + userDetails.getUsername());
                System.out.println("UserDetails authorities: " + userDetails.getAuthorities());

                if(jwtUtil.validateToken(jwt, userDetails)) {
                    System.out.println("Token is valid");

                    Claims claims = jwtUtil.extractAllClaims(jwt);
                    String role = claims.get("role", String.class);
                    System.out.println("Role from JWT: " + role);

                    // Create authorities from the role in JWT
                    Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);
                    System.out.println("Authorities created: " + authorities);

                    // Use the authorities extracted from JWT token
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("Authentication set in SecurityContext");
                    System.out.println("Authenticated user: " + SecurityContextHolder.getContext().getAuthentication().getName());
                    System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                } else {
                    System.out.println("Token validation failed");
                }
            } catch (Exception e) {
                System.out.println("Error during authentication: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            if(username == null) {
                System.out.println("Username is null, skipping authentication");
            } else {
                System.out.println("User already authenticated");
            }
        }

        System.out.println("========== JWT Filter End ==========\n");
        filterChain.doFilter(request, response);
    }
}











