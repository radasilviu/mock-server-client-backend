package com.fortech.mockapp;

import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.entities.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fortech.mockapp.configuration.model.specification.SearchSpec.hasSearchTermInWantedFields;

@Service
public class Pager<T> {

    private Map<String, Object> pagedResponse;
    private Pageable paging;
    private Page page;
    private PagedRequest requestParams;
    private DynamicallySearchableRepository repository;

    public void setRequestParams(PagedRequest requestParams, DynamicallySearchableRepository repository) {
        this.requestParams = requestParams;
        this.repository = repository;
        setPagedResponse();
    }

    public Pager(){}

    public Map<String, Object> getPagedResponse() {
        return pagedResponse;
    }

    private void setPagedResponse(){
        setPaging();
        setPage();
        pagedResponse = assemblePagedResponse(page);
    }

    public Pageable getPaging() {
        return paging;
    }

    private void setPaging(){
        Sort sort = getSort();
        Integer pageNumber = requestParams.getPageNumber();
        Integer pageSize = requestParams.getPageSize();
        paging = PageRequest.of(pageNumber, pageSize, sort);
    }

    public Page<T> getPage() {
        return page;
    }

    private void setPage(){
        String searchTerm = requestParams.getSearchTerm();
        ArrayList<String> columnsToSearchIn = requestParams.getColumnsToSearchIn();
        page = repository.findAll(hasSearchTermInWantedFields(columnsToSearchIn, searchTerm), paging);
    }

    private Map<String, Object> assemblePagedResponse(Page<T> page) {
        Map<String, Object> responseBody = new HashMap<>();
        List<T> content = page.getContent();
        responseBody.put("data", content);
        responseBody.put("currentPage", page.getNumber());
        responseBody.put("totalItems", page.getTotalElements());
        return responseBody;
    }

    private Sort getSort() {
        String sortColumn = requestParams.getSortColumn();
        Sort sort = Sort.by(sortColumn);
        if(isAscending())
            sort.ascending();
        else
            sort.descending();
        return sort;
    }

    private boolean isAscending() {
        return requestParams.getSortDirection().equals("asc");
    }

    // Not sure if this belongs in the actual class, or in the set-up of tests
//    private void setMockPage(){
//        ArrayList<Book> bookList = new ArrayList<>();
//        bookList.add(new Book());
//        page = new PageImpl<>(bookList, paging, 100);
//    }
}
