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

import static com.fortech.mockapp.configuration.model.specification.BookSpec.hasSearchTermInWantedFields;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService = new BookServiceImpl(bookRepository);

    // Book entity has a public Id setter (which is stupid),
    // but how could we check the ID stays the same if we set it private?
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

        bookService.deleteBookById(bookId);

        Mockito.verify(bookRepository).deleteById(bookId);
    }

    @Test
    void whenGettingBookPagedResponse_ShouldCallProperRepositoryFindAllMethod() {
        ArrayList<String> columnsToSearchIn = new ArrayList<>();
        columnsToSearchIn.add("title");
        PagedRequest requestParams = new PagedRequest(
                10, 0, "term", "asc", "title", columnsToSearchIn
        );
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book());
        Pageable pageable = PageRequest.of(requestParams.getPageNumber(), requestParams.getPageSize(), Sort.by("title").ascending());
        Page<Book> expected = new PageImpl<>(bookList, pageable, 100);
        Mockito.when(bookRepository.findAll((Specification) any(), (Pageable) any())).thenReturn(expected);

        bookService.getBookPagedResponse(requestParams);

        Mockito.verify(bookRepository).findAll((Specification) any(), (Pageable) any());
    }
}
