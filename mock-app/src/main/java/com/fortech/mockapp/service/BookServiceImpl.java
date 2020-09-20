package com.fortech.mockapp.service;

import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }
    @Override
    public Book getBookByTitle(String bookTitle) {
        return null;
    }
    @Override
    public void saveBook(Book book) {

    }
    @Override
    public void deleteBookByTitle(String bookTitle) {

    }
}
