package com.fortech.mockapp.controllers;

import com.fortech.mockapp.model.CompanyModel;
import com.fortech.mockapp.repository.CompanyRepository;
import com.fortech.mockapp.request.CompanyListRequest;
import com.fortech.mockapp.response.CompanyListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping(path="/list")
    public @ResponseBody
    CompanyListResponse getAllCompanies(@RequestBody CompanyListRequest request) {
        int pageNumber = request.getOffset() / request.getLimit();
        Sort sort = null;

        if (request.getSortDirection().equals("desc")) {
            sort = Sort.by(request.getSortColumn()).descending();
        } else {
            sort = Sort.by(request.getSortColumn()).ascending();
        }

        Pageable page = PageRequest.of(pageNumber, request.getLimit(), sort);
        Page<CompanyModel> companies = companyRepository.findAll(page);
        CompanyListResponse response = new CompanyListResponse(companies.getTotalElements(), companies.getContent());
        return response;
    }

}
