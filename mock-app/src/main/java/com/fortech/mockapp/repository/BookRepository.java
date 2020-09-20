package com.fortech.mockapp.repository;

import com.fortech.mockapp.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
