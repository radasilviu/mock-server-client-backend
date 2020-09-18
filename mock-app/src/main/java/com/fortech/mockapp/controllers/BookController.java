package com.fortech.mockapp.controllers;

import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;
    @Autowired
    BookController(BookServiceImpl bookService){
        bookService = bookService;
    }
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok().body(books);
    }
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        return ResponseEntity.ok().body(book);
    }
    @PostMapping
    public ResponseEntity<ResponseMessage> saveBook(@RequestBody Book book) {
        bookService.saveBook(book);
        final ResponseMessage responseMessage = new ResponseMessage("Book successfully saved");
        return ResponseEntity.ok().body(responseMessage);
    }
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResponseMessage> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBookById(bookId);
        final ResponseMessage responseMessage = new ResponseMessage("Book successfully deleted");
        return ResponseEntity.ok().body(responseMessage);
    }
}
