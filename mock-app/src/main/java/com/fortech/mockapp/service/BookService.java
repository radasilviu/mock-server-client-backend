package com.fortech.mockapp.service;

import com.fortech.mockapp.PagedRequest;
import com.fortech.mockapp.entities.Book;

import java.util.Map;

public interface BookService {
    void updateBook(String bookId, Book book);
    void deleteBookById(String bookId);
    Map<String, Object> getBookPagedResponse(PagedRequest requestParams);
}
