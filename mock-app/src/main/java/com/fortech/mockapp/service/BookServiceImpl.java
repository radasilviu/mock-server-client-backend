package com.fortech.mockapp.service;

import com.fortech.mockapp.utility.Exceptions.DataLayerException;
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
    private Pager<Book, String> pager;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, Pager pager) {
        this.bookRepository = bookRepository;
        this.pager = pager;
        setPagerRepository();
    }

    private void setPagerRepository() {
        this.pager.setRepository(this.bookRepository);
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
        pager.setRequestParams(requestParams);
        return pager.getPagedResponse();
    }
}
