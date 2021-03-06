package com.fortech.mockapp.controllers;

import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.service.BookService;
import com.fortech.mockapp.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class BookControllerImpl implements BookController {

    private BookService bookService;

    @Autowired
    BookControllerImpl(BookServiceImpl bookService){
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getBookPagedResponse(PagedRequest requestParams) {
        Map<String, Object> responseBody = bookService.getPagedResponse(requestParams);
        return ResponseEntity.ok().body(responseBody);
    }

    @Override
    public ResponseEntity<ResponseMessage> updateBook(String bookId, Book book) {
        bookService.updateBook(bookId, book);
        final ResponseMessage responseMessage = new ResponseMessage("Book successfully saved");
        return ResponseEntity.ok().body(responseMessage);
    }

    @Override
    public ResponseEntity<ResponseMessage> deleteBook(String bookId) {
        bookService.deleteBook(bookId);
        final ResponseMessage responseMessage = new ResponseMessage("Book successfully deleted");
        return ResponseEntity.ok().body(responseMessage);
    }
}
