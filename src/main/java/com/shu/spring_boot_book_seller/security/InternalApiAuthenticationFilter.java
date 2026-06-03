package com.shu.spring_boot_book_seller.security;

import com.shu.spring_boot_book_seller.util.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class InternalApiAuthenticationFilter extends OncePerRequestFilter {

    private final String accessKey;

    public InternalApiAuthenticationFilter(String accessKey) {
        this.accessKey = accessKey;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        return !request.getRequestURI().startsWith("/api/internal");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String requestkey = SecurityUtils.extractAuthTokenFromRequest(request);

            if (requestkey == null || !requestkey.equals(accessKey)) {

                log.warn("Internal key endpoint requested without/wrong key uri:{}", request.getRequestURI());
                throw new RuntimeException("Unauthorized");
            }
            UserPrincipal user = UserPrincipal.createSuperUser();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception ex) {
            log.error("Could not set User authentication",ex);
        }
        filterChain.doFilter(request,response);
        }

    }

