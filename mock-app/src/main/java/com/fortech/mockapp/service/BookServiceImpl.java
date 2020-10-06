package com.fortech.mockapp.service;

import com.fortech.mockapp.DataLayerException;
import com.fortech.mockapp.Pager;
import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void updateBook(String bookId, Book newBook) throws DataLayerException {
        Book bookToUpdate = bookRepository.findById(bookId)
                .orElseThrow(() -> new DataLayerException(
                "Could not find book with that ID", HttpStatus.NOT_FOUND
        ));
        bookToUpdate.setAuthor(newBook.getAuthor());
        bookToUpdate.setPrice(newBook.getPrice());
        bookToUpdate.setTitle(newBook.getTitle());
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
