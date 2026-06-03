package com.shu.spring_boot_book_seller.security;

import com.shu.spring_boot_book_seller.model.User;
import com.shu.spring_boot_book_seller.service.IUserService;
import com.shu.spring_boot_book_seller.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final IUserService userService;

    public CustomUserDetailsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));

        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name()));
        return UserPrincipal.builder().user(user).id(user.getId())
                .username(username).password(user.getPassword()).authorities(authorities).
                build();
    }
}
