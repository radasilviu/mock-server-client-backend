package com.fortech.mockapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DynamicallySearchableRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    Page<T> findAll(Specification bookSpecification, Pageable paging);
}
