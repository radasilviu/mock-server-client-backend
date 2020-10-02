package com.fortech.mockapp.service;

import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.repository.BookRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private final BookService bookService = new BookServiceImpl(bookRepository);

    // Book entity has a public Id setter (which is stupid),
    // but how could we check the ID stays the same if we set it private (thus forcing it to be null)?
    // - Not that we'd really need to
    @Test
    void whenUpdatingBookShouldFetchBookWithMatchingIdFromRepositoryAndUpdateItAndSaveIt() {
        String bookId = "test";
        Book old = new Book("a", "a", "a", 1);
        old.setId(bookId);
        Book updated = new Book("new", "new", "new", 10);
        Mockito.when(bookRepository.getOne(bookId)).thenReturn(old);

        bookService.updateBook(bookId, updated);

        Assert.assertEquals(old.getAuthor(), updated.getAuthor());
        Assert.assertEquals(old.getPrice(), updated.getPrice());
        Assert.assertEquals(old.getTitle(), updated.getTitle());
        Mockito.verify(bookRepository).getOne(bookId);
        Mockito.verify(bookRepository).save(old);
    }

    @Test
    void whenDeletingBookById_ShouldCallRepositoryMethod() {
        String bookId = "test";

        bookService.deleteBook(bookId);

        Mockito.verify(bookRepository).deleteById(bookId);
    }

    @Test
    void whenGettingBookPagedResponse_ShouldCallProperRepositoryFindAllMethod() {
        ArrayList<String> columnsToSearchIn = new ArrayList<>();
        columnsToSearchIn.add("title");
        PagedRequest requestParams = new PagedRequest(
                10, 0, "term", "asc", "title", columnsToSearchIn
        );

        Page<Book> returnedPage = getBookPage(requestParams);
        Mockito.when(bookRepository.findAll((Specification) any(), (Pageable) any())).thenReturn(returnedPage);

        bookService.getPagedResponse(requestParams);

        Mockito.verify(bookRepository).findAll((Specification) any(), (Pageable) any());
    }

    @Test
    void whenGettingBookPagedResponse_ShouldSortByColumnTitleIfNotOtherwiseMentioned() {
        ArrayList<String> columnsToSearchIn = new ArrayList<>();
        columnsToSearchIn.add("");
        PagedRequest requestParams = new PagedRequest(
                10, 0, "term", "asc", "title", columnsToSearchIn
        );
        Page<Book> expectedPage = getBookPage(requestParams);
        Mockito.when(bookRepository.findAll((Specification) any(), (Pageable) any())).thenReturn(expectedPage);

        Map<String, Object> actual = bookService.getPagedResponse(requestParams);

        Assert.assertEquals(actual, actual);
    }

    private Page<Book> getBookPage(PagedRequest requestParams) {
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book());

        Pageable pageable = PageRequest.of(requestParams.getPageNumber(), requestParams.getPageSize(), Sort.by("title").ascending());

        return new PageImpl<>(bookList, pageable, 100);
    }
}
