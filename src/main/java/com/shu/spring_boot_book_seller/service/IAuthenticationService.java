package com.shu.spring_boot_book_seller.service;

import com.shu.spring_boot_book_seller.model.User;

public interface IAuthenticationService {
    User signInAndReturnJwt(User signInRequest);
}
