package com.shu.spring_boot_book_seller.service;

import com.shu.spring_boot_book_seller.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IUserService {
    User saveUser(User user);

    Optional<User> findByUsername(String username);

    @Transactional
    void makeAdmin(String username);
}
