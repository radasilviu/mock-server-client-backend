package com.fortech.mockapp.service;

import com.fortech.mockapp.Pager;
import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void updateBook(String bookId, Book book) {
        Book bookToUpdate = bookRepository.getOne(bookId);
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPrice(book.getPrice());
        bookToUpdate.setTitle(book.getTitle());
        bookRepository.save(bookToUpdate);
    }

    @Override
    public void deleteBook(String bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Map<String, Object> getPagedResponse(PagedRequest requestParams) {
        Pager<Book> pager = new Pager<>(requestParams, bookRepository);
        return pager.getPagedResponse();
    }
}
