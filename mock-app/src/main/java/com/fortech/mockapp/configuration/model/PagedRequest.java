package com.fortech.mockapp.configuration.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PagedRequest {
    private Integer pageSize;
    private Integer offset;
    private String searchTerm;
    private String sortDirection;
    private String sortColumn;
    private ArrayList<String> columnsToSearchIn;

    public Integer getPageNumber() {
        return offset / pageSize;
    }

    public PagedRequest(Integer pageSize, Integer offset, String searchTerm, String sortDirection, String sortColumn, ArrayList<String> columnsToSearchIn) {
        this.pageSize = pageSize;
        this.offset = offset;
        this.searchTerm = searchTerm;
        this.sortDirection = sortDirection;
        this.sortColumn = sortColumn;
        this.columnsToSearchIn = columnsToSearchIn;
    }
}

