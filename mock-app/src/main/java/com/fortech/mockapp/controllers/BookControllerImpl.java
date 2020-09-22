package com.fortech.mockapp.controllers;

import com.fortech.mockapp.PagedRequest;
import com.fortech.mockapp.PagedResponse;
import com.fortech.mockapp.configuration.model.ResponseMessage;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.service.BookService;
import com.fortech.mockapp.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookControllerImpl implements BookController {

    private BookService bookService;
    @Autowired
    BookControllerImpl(BookServiceImpl bookService){
        this.bookService = bookService;
    }
    @Override
    public ResponseEntity<PagedResponse> getBookPagedResponse(PagedRequest pagedRequest) {
        Integer pageNumber = pagedRequest.getPageNumber();
        String searchTerm = pagedRequest.getSearchTerm();
        PagedResponse pagedResponse = bookService.getBookPagedResponse(pageNumber, searchTerm);
        return ResponseEntity.ok().body(pagedResponse);
    }
    @Override
    public ResponseEntity<ResponseMessage> saveBook(Book book) {
        bookService.saveBook(book);
        final ResponseMessage responseMessage = new ResponseMessage("Book successfully saved");
        return ResponseEntity.ok().body(responseMessage);
    }
    @Override
    public ResponseEntity<ResponseMessage> deleteBook(String bookTitle) {
        bookService.deleteBookByTitle(bookTitle);
        final ResponseMessage responseMessage = new ResponseMessage("Book successfully deleted");
        return ResponseEntity.ok().body(responseMessage);
    }
}
