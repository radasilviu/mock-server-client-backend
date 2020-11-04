package com.fortech.mockapp;

import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.repository.BookRepository;
import com.fortech.mockapp.service.Pager;
import com.fortech.mockapp.utility.Exceptions.NoRepositorySetException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;

// Tests only with Book Objects
@ExtendWith(MockitoExtension.class)
class PagerTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private BookRepository bookRepository;

    private Pager<Book, String> bookPager;
    private PagedRequest requestParams;

    @BeforeEach
    void setup(){
        bookPager = new Pager<>();
        setUpRequestParams("asc");
        bookPager.setRequestParams(requestParams);
    }

    @AfterEach
    void resetAll(){
        reset(bookRepository);
        requestParams = null;
    }

    @Test
    void getPagination_ShouldProperlySetPagination(){
        Pageable actual = bookPager.getPagination();

        Assert.assertEquals((int)requestParams.getPageNumber(), actual.getPageNumber());
        Assert.assertEquals(requestParams.getSortDirection(), (actual.getSort().getOrderFor(requestParams.getSortColumn()).getDirection()).toString().toLowerCase());
        Assert.assertNotNull(actual.getSort().getOrderFor("title"));
        Assert.assertEquals((int)requestParams.getOffset(), actual.getOffset());
    }

    @Test
    void getPagination_ShouldProperlySetPagination_Desc(){
        requestParams.setSortDirection("desc");
        Pageable actual = bookPager.getPagination();

        Assert.assertEquals((int)requestParams.getPageNumber(), actual.getPageNumber());
        Assert.assertEquals(requestParams.getSortDirection(), (actual.getSort().getOrderFor(requestParams.getSortColumn()).getDirection()).toString().toLowerCase());
        Assert.assertNotNull(actual.getSort().getOrderFor("title"));
        Assert.assertEquals((int)requestParams.getOffset(), actual.getOffset());
    }

    @Test
    void getPagination_ShouldProperlySetPagination_Unsorted(){
        requestParams.setSortDirection("");
        Pageable actual = bookPager.getPagination();

        Assert.assertEquals((int)requestParams.getPageNumber(), actual.getPageNumber());
        Assert.assertEquals("UNSORTED", actual.getSort().toString());
        Assert.assertNull(actual.getSort().getOrderFor("title"));
        Assert.assertEquals((int)requestParams.getOffset(), actual.getOffset());
    }

    @Test
    void getPage_ShouldThrowWhenRepositoryNotSet() {
        bookPager.setRequestParams(requestParams);

        Assert.assertThrows(NoRepositorySetException.class,() -> bookPager.getPageWithPagination());
    }

    @Test
    void getPage_ShouldCallRepositoryMethod() {
        bookPager.setRepository(bookRepository);

        bookPager.getPageWithPagination();

        Mockito.verify(bookRepository).findAll((Specification) any(), (Pageable) any());
    }

    @Test
    void getPagedResponse_ShouldCallRepositoryMethodWithProperParameters() {
        bookPager.setRepository(bookRepository);
        Pageable expectedPaging = returnPaging();

        bookPager.getPageWithPagination();

        Mockito.verify(bookRepository).findAll((Specification) any(), (Pageable) any());
        Assert.assertEquals(expectedPaging, bookPager.getPagination());
    }

    @Test
    void getPagedResponse_shouldReturnProperMapObject() {
        Mockito.when(bookRepository.findAll((Specification) any(), (Pageable) any())).thenReturn(returnDemoBookPage());
        bookPager.setRepository(bookRepository);
        bookPager.setRequestParams(requestParams);

        Map<String, Object> expected = returnPreparedForJsonFormat(returnDemoBookPage());
        Map<String, Object> actual = bookPager.getPagedResponse();

        Assert.assertEquals(expected.get("data").toString(), actual.get("data").toString());
    }

    private void setUpRequestParams(String sortDirection){
        ArrayList<String> columnsToSearchIn = new ArrayList<>();
        columnsToSearchIn.add("");
        requestParams = new PagedRequest(
                10, 0, "", sortDirection, "title", columnsToSearchIn
        );
    }

    private Page<Book> returnDemoBookPage(){
        ArrayList<Book> bookList = returnDemoBooks();
        return new PageImpl<>(bookList, returnPaging(), 4);
    }

    private ArrayList<Book> returnDemoBooks() {
        return new ArrayList<>(Arrays.asList(
                new Book("aaa", "demo1", "mock2", 1337),
                new Book("bbb", "demo1", "mock2", 1337),
                new Book("ccc", "demo1", "mock2", 1337),
                new Book("ddd", "demo1", "mock2", 1337)
        ));
    }

    private Pageable returnPaging(){
        setUpRequestParams("asc");
        Integer pageNumber = requestParams.getPageNumber();
        Integer pageSize = requestParams.getPageSize();
        Sort sort = Sort.by("title").ascending();
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private Map<String, Object> returnPreparedForJsonFormat(Page<Book> page) {
        Map<String, Object> responseBody = new HashMap<>();
        List<Book> content = page.getContent();
        responseBody.put("data", content);
        responseBody.put("currentPage", page.getNumber());
        responseBody.put("totalItems", page.getTotalElements());
        return responseBody;
    }
}
