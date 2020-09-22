package com.fortech.mockapp.controllers;

import com.fortech.mockapp.PagedRequest;
import com.fortech.mockapp.PagedResponse;
import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.entities.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books")
public interface BookController {
    @PostMapping(path = "/")
    ResponseEntity<PagedResponse> getBookPagedResponse(@RequestBody PagedRequest pagedRequest);
    @PostMapping("/save")
    ResponseEntity<ResponseMessage> saveBook(@RequestBody Book book);
    @DeleteMapping("/{bookTitle}")
    ResponseEntity<ResponseMessage> deleteBook(@PathVariable String bookTitle);
}
