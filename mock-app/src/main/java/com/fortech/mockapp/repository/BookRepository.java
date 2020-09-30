package com.fortech.mockapp.repository;

import com.fortech.mockapp.DynamicallySearchableRepository;
import com.fortech.mockapp.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BookRepository extends DynamicallySearchableRepository<Book, String> {
      Page<Book> findAll(Specification bookSpecification, Pageable paging);
}


