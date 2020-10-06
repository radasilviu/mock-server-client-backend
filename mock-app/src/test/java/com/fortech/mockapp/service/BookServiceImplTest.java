package com.fortech.mockapp.service;

import com.fortech.mockapp.DataLayerException;
import com.fortech.mockapp.Pager;
import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.repository.BookRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    private PagedRequest requestParams;

    @InjectMocks
    private final BookService bookService = new BookServiceImpl(bookRepository);

    @BeforeEach
    public void setUp(){
        reset(bookRepository);
    }

    @BeforeEach
    public void setUpRequestParams(){
        ArrayList<String> columnsToSearchIn = new ArrayList<>();
        columnsToSearchIn.add("");
        requestParams = new PagedRequest(
                10, 0, "term", "asc", "title", columnsToSearchIn
        );
    }

    // Book entity has a public Id setter (which is stupid),
    // but how could we check the ID stays the same if we set it private (thus forcing it to be null)?
    // - Not that we'd really need to
    @Test
    void whenUpdatingBookShouldFetchBookWithMatchingIdFromRepositoryAndUpdateItAndSaveIt() {
        String bookId = "test";
        Book old = new Book("a", "a", "a", 1);
        old.setId(bookId);
        Book updated = new Book("new", "new", "new", 10);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(old));

        bookService.updateBook(bookId, updated);

        Assert.assertEquals(old.getAuthor(), updated.getAuthor());
        Assert.assertEquals(old.getPrice(), updated.getPrice());
        Assert.assertEquals(old.getTitle(), updated.getTitle());
        Mockito.verify(bookRepository).findById(bookId);
        Mockito.verify(bookRepository).save(old);
    }

    @Test
    void whenUpdatingBookShouldThrowDataLayerExceptionWithMessageAndStatusCode() {
        String bookId = "test";
        Book updated = new Book("new", "new", "new", 10);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        DataLayerException exception = assertThrows(DataLayerException.class,() -> bookService.updateBook(bookId, updated));

        assertTrue(exception.getMessage().equals("Could not find book with that ID"));
        assertTrue(exception.getHttpStatus().equals(HttpStatus.NOT_FOUND));
        Mockito.verify(bookRepository).findById(bookId);
    }

    @Test
    void whenDeletingBookById_ShouldCallRepositoryMethod() {
        String bookId = "test";

        bookService.deleteBook(bookId);

        Mockito.verify(bookRepository).deleteById(bookId);
    }

    @Test
    void whenGettingBookPagedResponse_ShouldCallProperRepositoryFindAllMethod() {
        Pager<Book> testPager = new Pager<>(requestParams);

        Mockito.when(bookRepository.findAll((Specification) any(), (Pageable) any())).thenReturn(testPager.getPage());

        bookService.getPagedResponse(requestParams);

        Mockito.verify(bookRepository).findAll((Specification) any(), (Pageable) any());
    }
}
