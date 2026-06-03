package com.shu.spring_boot_book_seller.security.jwt;

import com.shu.spring_boot_book_seller.security.UserPrincipal;
import com.shu.spring_boot_book_seller.util.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements IJwtProvider{

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrincipal auth) {//Creates JWT

        //Suppose user has: ROLE_USER
               // ROLE_ADMIN
        //This converts them into:ROLE_USER,ROLE_ADMIN
        String authorities = auth.getAuthorities().stream().
                map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

    return Jwts.builder().setSubject(auth.getUsername()).
            claim("roles", authorities).claim("userId", auth.getId())
            .setExpiration(new Date(System.currentTimeMillis()+ JWT_EXPIRATION_IN_MS))
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET ).compact();
    }

    @Override //Extract user info from token
    //Create Authentication object for Spring Security
    public Authentication getAuthentication(HttpServletRequest request){

        Claims claims = extractClaims(request);//Reads JWT from request and extracts payload.{
//        "sub":"john",
//                "roles":"ROLE_USER",
//                "userId":1

        if (claims==null){
            return null;
        }
        String username= claims.getSubject();
        Long userId =claims.get("userId", Long.class);

        Set<GrantedAuthority> authorities= Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority).collect(Collectors.toSet());//["ROLE_USER", "ROLE_ADMIN"]

        UserDetails userDetails= UserPrincipal.builder()
                .username(username).authorities(authorities)
                .id(userId).build();
        if(username==null){
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,authorities);

    }

    @Override
    public boolean validateToken(HttpServletRequest request){

        Claims claims = extractClaims(request);
        if (claims==null){
            return false;
        }
        if (claims.getExpiration().before(new Date())){
            return  false;
        }
        return true;
    }

    private Claims extractClaims(HttpServletRequest request) { //Reads JWT payload

        String token = SecurityUtils.extractAuthTokenFromRequest(request);

        if (token == null || token.trim().isEmpty()) {
            return null;
        }

        try {
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
