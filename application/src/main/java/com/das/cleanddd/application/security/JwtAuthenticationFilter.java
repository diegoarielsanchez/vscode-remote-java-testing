package com.das.cleanddd.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JWT Authentication Filter for processing and validating JWT tokens in request headers.
 * This filter intercepts requests and validates JWT tokens before they reach the application.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret:your-secret-key-change-this-in-production}")
    private String jwtSecret;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpirationMs;

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_CLAIM = "authorities";

    /**
     * Performs the actual filtering logic for JWT token validation.
     * Extracts the JWT token from the Authorization header, validates it,
     * and sets the authentication in the SecurityContext if valid.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = extractJwtFromRequest(request);
            
            if (jwt != null && validateToken(jwt)) {
                Authentication authentication = getAuthenticationFromToken(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("JWT Token validated successfully for user: " + authentication.getName());
            }
        } catch (JwtException e) {
            logger.error("JWT validation failed: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Cannot set user authentication in security context", e);
        }
        
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts JWT token from the Authorization header.
     * 
     * @param request the HTTP request
     * @return the JWT token or null if not found
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        
        return null;
    }

    /**
     * Validates the JWT token using the secret key.
     * 
     * @param token the JWT token to validate
     * @return true if token is valid, false otherwise
     */
    private boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(new javax.crypto.spec.SecretKeySpec(
                    jwtSecret.getBytes(), 
                    0, 
                    jwtSecret.getBytes().length, 
                    "HmacSHA256"))
                .build()
                .parseSignedClaims(token);
            
            logger.debug("JWT token validated successfully");
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("JWT token validation failed", e);
            return false;
        }
    }

    /**
     * Extracts claims from the JWT token and creates an Authentication object.
     * 
     * @param token the JWT token
     * @return Authentication object with user details and authorities
     */
    private Authentication getAuthenticationFromToken(String token) {
        Claims claims = extractClaimsFromToken(token);
        
        String username = claims.getSubject();
        
        @SuppressWarnings("unchecked")
        List<String> authoritiesStrings = (List<String>) claims.get(AUTHORITIES_CLAIM, List.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if (authoritiesStrings != null) {
            for (String authority : authoritiesStrings) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        } else {
            // Default to USER role if no authorities found
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    /**
     * Extracts all claims from the JWT token.
     * 
     * @param token the JWT token
     * @return Claims object containing token data
     */
    private Claims extractClaimsFromToken(String token) {
        return Jwts.parser()
            .verifyWith(new javax.crypto.spec.SecretKeySpec(
                jwtSecret.getBytes(), 
                0, 
                jwtSecret.getBytes().length, 
                "HmacSHA256"))
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    /**
     * Extracts the username from the JWT token.
     * 
     * @param token the JWT token
     * @return the username (subject claim)
     */
    public String extractUsername(String token) {
        return extractClaimsFromToken(token).getSubject();
    }

    /**
     * Checks if the token is expired.
     * 
     * @param token the JWT token
     * @return true if token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        Claims claims = extractClaimsFromToken(token);
        return claims.getExpiration().before(new java.util.Date());
    }
}