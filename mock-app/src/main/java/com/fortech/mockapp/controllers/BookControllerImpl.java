package com.fortech.mockapp.controllers;

import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookControllerImpl implements BookController {

    private BookService bookService;
    @Autowired
    BookControllerImpl(BookServiceImpl bookservice){
        bookService = bookservice;
    }
    @Override
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok().body(books);
    }
    @Override
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        return ResponseEntity.ok().body(book);
    }
    @Override
    public ResponseEntity<ResponseMessage> saveBook(@RequestBody Book book, @PathVariable Long bookId) {
        bookService.saveBook(book);
        final ResponseMessage responseMessage = new ResponseMessage("Book successfully saved");
        return ResponseEntity.ok().body(responseMessage);
    }
    @Override
    public ResponseEntity<ResponseMessage> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBookById(bookId);
        final ResponseMessage responseMessage = new ResponseMessage("Book successfully deleted");
        return ResponseEntity.ok().body(responseMessage);
    }
}
