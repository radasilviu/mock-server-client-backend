package com.fortech.mockapp.service;

import com.fortech.mockapp.PagedRequest;
import com.fortech.mockapp.entities.Book;

import java.util.Map;

public interface BookService {
    void saveBook(Book book);
    void deleteBookByTitle(String bookTitle);
    Map<String, Object> getBookPagedResponse(PagedRequest requestParams);
}
