package com.fortech.mockapp.service;

import com.fortech.mockapp.model.CompanyModel;
import com.fortech.mockapp.repository.CompanyRepository;
import com.fortech.mockapp.request.CompanyListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
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

    public Page list(CompanyListRequest request) {
        int pageNumber = request.getOffset() / request.getLimit();
        Sort sort = null;

        if (request.getSortDirection().equals("desc")) {
            sort = Sort.by(request.getSortColumn()).descending();
        } else {
            sort = Sort.by(request.getSortColumn()).ascending();
        }

        Pageable pageable = PageRequest.of(pageNumber, request.getLimit(), sort);
        Page page = findByFilter(request.getFilter(), pageable, request.getColumns());
        return page;
    }

    public Page findByFilter(String filter, Pageable pageable, ArrayList<String> columns) {
            Page page = companyRepository.findAll(new Specification<CompanyModel>() {
            @Override
            public Predicate toPredicate(Root<CompanyModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!filter.equals("")) {
                    for (String column : columns) {
                        if (column.contains(".")) {
                            String[] relationPath = explodeModelRelations(column);

                            predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get("clients").get("name").as(String.class)
                                    ), "%" + filter.toLowerCase() + "%"
                            ));

                            for (String item : relationPath) {
                            }
                        } else {
                            predicates.add(createLikeCriteria(criteriaBuilder, root, column, filter));
                        }
                    }
                } else {
                    predicates.add(criteriaBuilder.equal(criteriaBuilder.literal(1), 1));
                }
                return criteriaBuilder.or(
                    predicates.toArray(new Predicate[] {})
                );
            }
        }, pageable);
        return page;
    }

    private String[] explodeModelRelations(String relationPath) {
        String[] split = relationPath.split(".");
        return split;
    }

    private Predicate createLikeCriteria(CriteriaBuilder criteriaBuilder, Root<CompanyModel> root, String column, String filter) {
        return criteriaBuilder.like(
            criteriaBuilder.lower(
                    root.get(column).as(String.class)
            ), "%" + filter.toLowerCase() + "%"
        );
    }
}
