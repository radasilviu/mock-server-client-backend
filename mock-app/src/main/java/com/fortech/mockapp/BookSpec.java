package com.fortech.mockapp;

import com.fortech.mockapp.entities.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BookSpec {
    public static Specification<Book> hasSeachTermInWantedFields(ArrayList<String> fieldsToSearchIn, String searchTerm) {
        return (Specification<Book>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String fieldName:
                    fieldsToSearchIn) {
                predicates.add(builder.like(root.get(fieldName), "%"+searchTerm+"%"));
                System.out.println(fieldName);
                System.out.println(searchTerm);
            }
            return builder.or(predicates.toArray(new Predicate[] {}));
        };
    }
}
