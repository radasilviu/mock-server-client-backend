package com.fortech.mockapp.service;

import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.entities.Book;

import java.util.Map;

public interface BookService {
    void updateBook(String bookId, Book book);
    void deleteBook(String bookId);
    Map<String, Object> getBookPagedResponse(PagedRequest requestParams);
}
