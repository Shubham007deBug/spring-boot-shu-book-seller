package com.shu.spring_boot_book_seller.service;

import com.shu.spring_boot_book_seller.model.Book;
import com.shu.spring_boot_book_seller.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService implements IBookService{

    @Autowired
    private IBookRepository iBookRepository;

    @Transactional
    @Override
    public Book saveBook(Book book) {
        book.setCreateTime(LocalDateTime.now());
        return iBookRepository.save(book);

    }

    @Transactional
    @Override
    public void deleteBook(Long id){
       iBookRepository.deleteById(id);
    }

    @Override
    public List<Book> findAllBook(){
        return iBookRepository.findAll();
    }



}
