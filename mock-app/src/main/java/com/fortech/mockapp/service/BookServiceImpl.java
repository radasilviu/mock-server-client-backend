package com.fortech.mockapp.service;

import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fortech.mockapp.configuration.model.specification.BookSpec.hasSearchTermInWantedFields;

@Service
public class BookServiceImpl implements BookService{

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        _createTestData();
    }

    private void _createTestData(){
        bookRepository.save(new Book("Aventurile lui costel", "costel", "adevar", 11));
        bookRepository.save(new Book("Aventurile lui mirel", "adi", "neadevar", 1100));
        bookRepository.save(new Book("Aventurile lui abel", "mirel", "neadevar", 1100));
        bookRepository.save(new Book("Aventurile lui canel", "adi", "mirel", 1100));
        ArrayList<Book> arr = new ArrayList<Book>();
        for(int i = 0; i < 200 ; i++)
            arr.add(new Book("Aventurile lui Gigel"+i, "Gogel", "adv", 1));
        bookRepository.saveAll(arr);
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
    public void deleteBookById(String bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Map<String, Object> getBookPagedResponse(PagedRequest requestParams) {
        Pageable paging = getPaging(requestParams);

        Page<Book> bookPage = getBookPage(requestParams, paging);
        List<Book> bookList = bookPage.getContent();

        return assembleResponseBody(bookPage, bookList);
    }

    private Page<Book> getBookPage(PagedRequest requestParams, Pageable paging) {
        String searchTerm = requestParams.getSearchTerm();
        ArrayList<String> columnsToSearchIn = requestParams.getColumnsToSearchIn();
        return bookRepository.findAll(hasSearchTermInWantedFields(columnsToSearchIn, searchTerm), paging);
    }

    private Pageable getPaging(PagedRequest requestParams) {
        Sort sort = getSort(requestParams);
        Integer pageNumber = requestParams.getOffset() / requestParams.getPageSize();
        Integer pageSize = requestParams.getPageSize();
        Pageable paging = PageRequest.of(pageNumber, pageSize, sort);
        return paging;
    }

    private Map<String, Object> assembleResponseBody(Page<Book> bookPage, List<Book> bookList) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("books", bookList);
        responseBody.put("currentPage", bookPage.getNumber());
        responseBody.put("totalItems", bookPage.getTotalElements());
        return responseBody;
    }

    private Sort getSort(PagedRequest requestParams) {
        Sort sort = Sort.by(requestParams.getSortColumn());
        if(isAscending(requestParams))
            sort.ascending();
        else
            sort.descending();
        return sort;
    }

    private boolean isAscending(PagedRequest requestParams) {
        return requestParams.getSortDirection() == "asc";
    }
}
