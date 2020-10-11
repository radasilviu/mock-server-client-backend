package com.fortech.mockapp.service;

import com.fortech.mockapp.configuration.model.PagedRequest;
import com.fortech.mockapp.repository.DynamicallySearchableRepository;
import com.fortech.mockapp.utility.Exceptions.NoRepositorySetException;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fortech.mockapp.configuration.model.specification.SearchSpec.hasSearchTermInWantedFields;


// you can create this method without setting a repository - which kinda sucks,
// but is hard to avoid if we want Spring to manage this component
// - we could change getPage method to take the repository as a parameter, but that sounds meh
@Service
@Scope("prototype")
public class Pager<T, ID> {
    private PagedRequest requestParams;
    private DynamicallySearchableRepository<T, ID> repository;

    public void setRepository(DynamicallySearchableRepository<T, ID> repository) {
        this.repository = repository;
    }

    public void setRequestParams(PagedRequest requestParams) {
        this.requestParams = requestParams;
    }

    public Map<String, Object> getPagedResponse() {
        Page<T> formattedPage = getPageWithPagination();
        return prepareForJsonFormat(formattedPage);
    }

    public Page<T> getPageWithPagination() throws NoRepositorySetException {
        if(repository == null)
            throw new NoRepositorySetException("No repository was set, please make sure to call setter method on Pager instance");
        Pageable pagination = getPagination();
        return getPage(pagination);
    }

    public Pageable getPagination() {
        Sort sort = getSort();
        Integer pageNumber = requestParams.getPageNumber();
        Integer pageSize = requestParams.getPageSize();
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private Page<T> getPage(Pageable pagination){
        String searchTerm = requestParams.getSearchTerm();
        ArrayList<String> columnsToSearchIn = requestParams.getColumnsToSearchIn();
        return repository.findAll(hasSearchTermInWantedFields(columnsToSearchIn, searchTerm), pagination);
    }

    private Map<String, Object> prepareForJsonFormat(Page<T> page) {
        Map<String, Object> responseBody = new HashMap<>();
        List<T> content = page.getContent();
        responseBody.put("data", content);
        responseBody.put("currentPage", page.getNumber());
        responseBody.put("totalItems", page.getTotalElements());
        return responseBody;
    }

    private Sort getSort() {
        String sortColumn = requestParams.getSortColumn();
        Sort sort = Sort.unsorted();
        switch (requestParams.getSortDirection()){
            case "asc": sort = Sort.by(sortColumn).ascending();
                break;
            case "desc": sort = Sort.by(sortColumn).descending();
                break;
        }
        return sort;
    }
}
