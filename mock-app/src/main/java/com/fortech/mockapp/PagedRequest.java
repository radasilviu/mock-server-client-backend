package com.fortech.mockapp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedRequest {
    private Integer pageNumber;
    private  String searchTerm;

}

