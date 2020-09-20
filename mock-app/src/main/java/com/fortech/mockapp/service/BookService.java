package com.fortech.mockapp.service;

import com.fortech.mockapp.entities.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookByTitle(String bookTitle);
    void saveBook(Book book);
    void deleteBookByTitle(String bookTitle);
}
