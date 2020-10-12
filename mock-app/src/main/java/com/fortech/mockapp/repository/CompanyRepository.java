package com.fortech.mockapp.repository;

import com.fortech.mockapp.entities.CompanyModel;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CompanyRepository extends
        DynamicallySearchableRepository<CompanyModel, Integer> {

}
