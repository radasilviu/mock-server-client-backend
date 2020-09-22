package com.fortech.mockapp.repository;

import com.fortech.mockapp.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
