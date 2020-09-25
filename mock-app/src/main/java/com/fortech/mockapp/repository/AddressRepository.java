package com.fortech.mockapp.repository;

import com.fortech.mockapp.model.AddressModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends
        PagingAndSortingRepository<AddressModel, Integer> {
}
