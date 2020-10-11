package com.fortech.mockapp.response;

import com.fortech.mockapp.entities.CompanyModel;

import java.util.List;

public class CompanyListResponse {
    private long filtered;

    private List<CompanyModel> data;

    public CompanyListResponse(long filtered, List<CompanyModel> data) {
        this.filtered = filtered;
        this.data = data;
    }

    public long getFiltered() {
        return filtered;
    }

    public void setFiltered(long filtered) {
        this.filtered = filtered;
    }

    public List<CompanyModel> getData() {
        return data;
    }

    public void setData(List<CompanyModel> data) {
        this.data = data;
    }
}
