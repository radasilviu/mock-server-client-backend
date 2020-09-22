package com.fortech.mockapp;

import com.fortech.mockapp.entities.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResponse {
    private List<Book> books;
    private Integer bookCount;
}
