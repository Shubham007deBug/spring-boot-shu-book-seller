package com.shu.spring_boot_book_seller.service;

import com.shu.spring_boot_book_seller.model.User;
import com.shu.spring_boot_book_seller.security.UserPrincipal;
import com.shu.spring_boot_book_seller.security.jwt.IJwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IJwtProvider jwtProvider;

    @Override
    public User signInAndReturnJwt(User signInRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        User signInUser = userPrincipal.getUser();
        signInUser.setToken(jwt);
        return signInUser;
    }

}
