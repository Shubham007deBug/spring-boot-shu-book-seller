package com.shu.spring_boot_book_seller.security.jwt;

import com.shu.spring_boot_book_seller.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface IJwtProvider {
    String generateToken(UserPrincipal auth);

    Authentication getAuthentication (HttpServletRequest request);

    boolean validateToken(HttpServletRequest request);
}
