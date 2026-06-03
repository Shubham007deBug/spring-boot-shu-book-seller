package com.shu.spring_boot_book_seller.repository;

import com.shu.spring_boot_book_seller.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book,Long> {
}
