package com.fortech.mockapp.repository;

import com.fortech.mockapp.model.CompanyModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CompanyRepository extends
        PagingAndSortingRepository<CompanyModel, Integer>, JpaSpecificationExecutor<CompanyModel> {

}
