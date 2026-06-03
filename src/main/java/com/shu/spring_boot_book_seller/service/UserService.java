package com.shu.spring_boot_book_seller.service;

import com.shu.spring_boot_book_seller.model.Role;
import com.shu.spring_boot_book_seller.model.User;
import com.shu.spring_boot_book_seller.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository iUserRepository;


    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
@Transactional
public User saveUser(User user) {

     user.setPassword(passwordEncoder.encode(user.getPassword()));
     user.setRole(Role.USER);
     user.setCreateTime(LocalDateTime.now());
     return iUserRepository.save(user);
 }
@Override
public Optional<User> findByUsername(String username) {
    return iUserRepository.findByUsername(username);
}

@Transactional
@Override
public void makeAdmin(String username){
    iUserRepository.updateUserRole(username, Role.ADMIN);
}
}
