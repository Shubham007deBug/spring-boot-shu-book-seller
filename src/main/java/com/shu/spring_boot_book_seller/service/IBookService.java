package com.shu.spring_boot_book_seller.service;

import com.shu.spring_boot_book_seller.model.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBookService {
    @Transactional
    Book saveBook(Book book);

    @Transactional
    void deleteBook(Long id);

    List<Book> findAllBook();
}
