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
        _createTestData();
    }

    private void _createTestData(){
        bookRepository.save(new Book("Aventurile lui costel", "costel", "adevar", 11));
        bookRepository.save(new Book("Aventurile lui mirel", "adi", "neadevar", 1100));
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
