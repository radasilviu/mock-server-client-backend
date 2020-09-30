package com.fortech.mockapp;

import com.fortech.mockapp.configuration.model.PagedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fortech.mockapp.configuration.model.specification.SearchSpec.hasSearchTermInWantedFields;

public class Pager<T> {

    private PagedRequest requestParams;
    private DynamicallySearchableRepository repository;

    public Pager(PagedRequest requestParams, DynamicallySearchableRepository repository) {
        this.requestParams = requestParams;
        this.repository = repository;
    }

    public Map<String, Object> getPagedResponse() {
        Pageable paging = getPaging();
        Page<T> page = getPage(paging);
        List<T> content = page.getContent();
        return assemblePagedResponse(page, content);
    }

    private Pageable getPaging() {
        Sort sort = getSort();
        Integer pageNumber = requestParams.getPageNumber();
        Integer pageSize = requestParams.getPageSize();
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private Page<T> getPage(Pageable paging) {
        String searchTerm = requestParams.getSearchTerm();
        ArrayList<String> columnsToSearchIn = requestParams.getColumnsToSearchIn();
        return repository.findAll(hasSearchTermInWantedFields(columnsToSearchIn, searchTerm), paging);
    }

    private Map<String, Object> assemblePagedResponse(Page<T> page, List<T> content) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("data", content);
        responseBody.put("currentPage", page.getNumber());
        responseBody.put("totalItems", page.getTotalElements());
        return responseBody;
    }

    private Sort getSort() {
        String sortColumn = requestParams.getSortColumn();
//        if(sortColumn == "")
//            sortColumn = "title";
        Sort sort = Sort.by(sortColumn);
        if(isAscending())
            sort.ascending();
        else
            sort.descending();
        return sort;
    }

    private boolean isAscending() {
        return requestParams.getSortDirection() == "asc";
    }
}
