package com.fortech.mockapp.repository;

import com.fortech.mockapp.model.ClientModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ClientRepository extends
        PagingAndSortingRepository<ClientModel, Integer> {
}
