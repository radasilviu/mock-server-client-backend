package com.fortech.mockapp.controllers;

import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.entities.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/book")
public interface BookController {
    @PostMapping(path = "/list")
    ResponseEntity<Map<String, Object>> getBookPagedResponse(@RequestBody PagedRequest requestParams);
    @PutMapping("/update/{bookId}")
    ResponseEntity<ResponseMessage> updateBook(@PathVariable String bookId, @RequestBody Book book);
    @DeleteMapping("/{bookId}")
    ResponseEntity<ResponseMessage> deleteBook(@PathVariable String bookId);
}
