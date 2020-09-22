package com.fortech.mockapp.controllers;

import com.fortech.mockapp.PagedRequest;
import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.entities.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/books")
public interface BookController {
    @PostMapping(path = "/")
    ResponseEntity<Map<String, Object>> getBookPagedResponse(@RequestBody PagedRequest requestParams);
    @PostMapping("/save")
    ResponseEntity<ResponseMessage> saveBook(@RequestBody Book book);
    @DeleteMapping("/{bookTitle}")
    ResponseEntity<ResponseMessage> deleteBook(@PathVariable String bookTitle);
}
