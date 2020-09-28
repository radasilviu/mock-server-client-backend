package com.fortech.mockapp.controllers;

import com.fortech.mockapp.PagedRequest;
import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.entities.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/book")
public interface BookController {
    @PostMapping(path = "/list")
    ResponseEntity<Map<String, Object>> getBookPagedResponse(@RequestBody PagedRequest requestParams);
    @PutMapping("/update")
    ResponseEntity<ResponseMessage> updateBook(@RequestBody String bookId, Book book);
    @DeleteMapping("/{bookTitle}")
    ResponseEntity<ResponseMessage> deleteBook(@PathVariable String bookId);
}
