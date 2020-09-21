package com.fortech.mockapp.repository;

import com.fortech.mockapp.model.CompanyModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<CompanyModel, Integer> {
}
