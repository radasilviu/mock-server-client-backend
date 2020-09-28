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
}

