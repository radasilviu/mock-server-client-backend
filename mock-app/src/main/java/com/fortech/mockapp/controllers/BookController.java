package com.fortech.mockapp.controllers;

import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.entities.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books")
public interface BookController {
    @GetMapping("/")
    ResponseEntity<List<Book>> getAllBooks();
    @GetMapping("/{bookId}")
    ResponseEntity<Book> getBookById(@PathVariable Long bookId);
    @PostMapping("/{bookId}")
    ResponseEntity<ResponseMessage> saveBook(@RequestBody Book book, @PathVariable Long bookId);
    @DeleteMapping("/{bookId}")
    ResponseEntity<ResponseMessage> deleteBook(@PathVariable Long bookId);
}
