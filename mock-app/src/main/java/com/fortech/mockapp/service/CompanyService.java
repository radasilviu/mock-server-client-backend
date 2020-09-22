package com.fortech.mockapp.service;

import com.fortech.mockapp.model.CompanyModel;
import com.fortech.mockapp.repository.CompanyRepository;
import com.fortech.mockapp.request.CompanyListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public CompanyModel update(int id, CompanyModel company) {
        Optional<CompanyModel> temp = companyRepository.findById(id);
        temp.get().setName(company.getName());
        return companyRepository.save(temp.get());
    }

    public void delete(int id) {
        companyRepository.deleteById(id);
    }

    public Page<CompanyModel> list(CompanyListRequest request) {
        int pageNumber = request.getOffset() / request.getLimit();
        Sort sort = null;

        if (request.getSortDirection().equals("desc")) {
            sort = Sort.by(request.getSortColumn()).descending();
        } else {
            sort = Sort.by(request.getSortColumn()).ascending();
        }

        Pageable page = PageRequest.of(pageNumber, request.getLimit(), sort);
        Page<CompanyModel> companies = companyRepository.findAll(page);
        return companies;
    }
}
