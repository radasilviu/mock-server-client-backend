package com.fortech.mockapp.controllers;

import com.fortech.mockapp.model.CompanyModel;
import com.fortech.mockapp.repository.CompanyRepository;
import com.fortech.mockapp.request.CompanyListRequest;
import com.fortech.mockapp.response.CompanyListResponse;
import com.fortech.mockapp.response.GeneralResponse;
import com.fortech.mockapp.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @PostMapping(path="/list")
    public @ResponseBody
    CompanyListResponse getAllCompanies(@RequestBody CompanyListRequest request) {
        Page<CompanyModel> companies = companyService.list(request);
        CompanyListResponse response = new CompanyListResponse(companies.getTotalElements(), companies.getContent());
        return response;
    }

    @PutMapping(path="/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody CompanyModel update(@PathVariable(value="id") String id, @RequestBody CompanyModel company) {
        return companyService.update(Integer.parseInt(id), company);
    }

    @DeleteMapping(path="{id}", produces = "application/json")
    public @ResponseBody GeneralResponse delete(@PathVariable(value="id") String id) {
        companyService.delete(Integer.parseInt(id));
        return new GeneralResponse("Deleted");
    }
}
