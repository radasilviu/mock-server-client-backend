package com.fortech.mockapp.repository;

import com.fortech.mockapp.DynamicallySearchableRepository;
import com.fortech.mockapp.model.CompanyModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface CompanyRepository extends
        DynamicallySearchableRepository<CompanyModel, Integer> {

}
