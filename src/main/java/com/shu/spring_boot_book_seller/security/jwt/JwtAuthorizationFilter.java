package com.shu.spring_boot_book_seller.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private IJwtProvider iJwtProvider;


    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        String uri = request.getRequestURI();

        return uri.startsWith("/api/internal")
                || uri.startsWith("/api/authentication")
                || "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = iJwtProvider.getAuthentication(request);

        if (authentication !=null && iJwtProvider.validateToken(request)){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);

    }
}
