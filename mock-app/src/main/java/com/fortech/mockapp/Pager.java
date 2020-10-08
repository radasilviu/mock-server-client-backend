package com.fortech.mockapp;

import com.fortech.mockapp.configuration.model.PagedRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fortech.mockapp.configuration.model.specification.SearchSpec.hasSearchTermInWantedFields;

@Service
@Scope("prototype")
public class Pager<T, ID> {

    private Map<String, Object> pagedResponse;
    private Pageable paging;
    private Page<T> page;
    private PagedRequest requestParams;
    private DynamicallySearchableRepository<T, ID> repository;

    public void setRepository(DynamicallySearchableRepository<T, ID> repository) {
        this.repository = repository;
    }

    public void setRequestParams(PagedRequest requestParams) {
//        assert repository != null;
        this.requestParams = requestParams;
        setPagedResponse();
    }

    public Map<String, Object> getPagedResponse() {
        return pagedResponse;
    }

    public Page<T> getPage() {
        return page;
    }

    public Pageable getPaging() {
        return paging;
    }

    private void setPagedResponse(){
        setPaging();
        setPage();
        pagedResponse = assemblePagedResponse(page);
    }

    private void setPaging(){
        Sort sort = getSort();
        Integer pageNumber = requestParams.getPageNumber();
        Integer pageSize = requestParams.getPageSize();
        paging = PageRequest.of(pageNumber, pageSize, sort);
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
}
