package com.fortech.mockapp.service;

import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.repository.BookRepository;

import java.util.List;

public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

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
