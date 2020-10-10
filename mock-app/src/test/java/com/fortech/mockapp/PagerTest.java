package com.fortech.mockapp;

import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.repository.BookRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        setUpRequestParams();
        bookPager.setRequestParams(requestParams);
    }

    @AfterEach
    void resetAll(){
        reset(bookRepository);
        setUpRequestParams();
        bookPager.setRequestParams(requestParams);
        requestParams = null;
    }

    @Test
    void getPagination_ShouldProperlySetPagination(){
        Pageable actual = bookPager.getPagination();
        Pageable expected = returnPaging();

        Assert.assertEquals(expected.getPageNumber(), actual.getPageNumber());
        Assert.assertEquals(expected.getSort(), actual.getSort());
        Assert.assertEquals(expected.getOffset(), actual.getOffset());

        Assert.assertEquals(expected, actual);
    }

    @Test
    void getPage_ShouldThrowWhenRepositoryNotSet() {
        bookPager.setRequestParams(requestParams);

        Assert.assertThrows(NoRepositorySetException.class,() -> bookPager.getPageWithPagination());
    }

    @Test
    void getPage_ShouldCallRepositoryMethod() {
        Mockito.when(bookRepository.findAll((Specification) any(), (Pageable) any())).thenReturn(returnEmptyPage());
        bookPager.setRepository(bookRepository);

        bookPager.getPageWithPagination();

        Mockito.verify(bookRepository).findAll((Specification) any(), (Pageable) any());
    }

    @Test
    void getPagedResponse_ShouldCallRepositoryMethodWithProperParameters() {
        Mockito.when(bookRepository.findAll((Specification) any(), (Pageable) any())).thenReturn(returnEmptyPage());
        bookPager.setRepository(bookRepository);
        Pageable expectedPaging = returnPaging();

        bookPager.getPageWithPagination();

        Mockito.verify(bookRepository).findAll((Specification) any(), (Pageable) any());
        Assert.assertEquals(expectedPaging, bookPager.getPagination());
    }

    @Test
    void getPagedResponse_shouldReturnProperMapObject() {
        Mockito.when(bookRepository.findAll((Specification) any(), (Pageable) any())).thenReturn(returnEmptyPage());
        bookPager.setRepository(bookRepository);
        bookPager.setRequestParams(requestParams);

        Map<String, Object> expected = prepareForJsonFormat(bookPager.getPageWithPagination());

        Assert.assertEquals(expected, bookPager.getPagedResponse());
    }

    private void setUpRequestParams(){
        ArrayList<String> columnsToSearchIn = new ArrayList<>();
        columnsToSearchIn.add("");
        requestParams = new PagedRequest(
                10, 0, "", "asc", "title", columnsToSearchIn
        );
    }

    private Page returnEmptyPage(){
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book());
        return new PageImpl<>(bookList, returnPaging(), 100);
    }

    private Pageable returnPaging(){
        setUpRequestParams();
        Integer pageNumber = requestParams.getPageNumber();
        Integer pageSize = requestParams.getPageSize();
        Sort sort = Sort.by("title").ascending();
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private Map<String, Object> prepareForJsonFormat(Page<Book> page) {
        Map<String, Object> responseBody = new HashMap<>();
        List<Book> content = page.getContent();
        responseBody.put("data", content);
        responseBody.put("currentPage", page.getNumber());
        responseBody.put("totalItems", page.getTotalElements());
        return responseBody;
    }
}
