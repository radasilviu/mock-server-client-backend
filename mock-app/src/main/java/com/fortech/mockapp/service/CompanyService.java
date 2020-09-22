package com.fortech.mockapp.service;

import com.fortech.mockapp.model.CompanyModel;
import com.fortech.mockapp.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
