package com.fortech.mockapp;

import com.fortech.mockapp.entities.Book;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class BookSpec {
    public static Specification<Book> hasSearchTermInWantedFields(ArrayList<String> fieldsToSearchIn, String searchTerm) {
        return (Specification<Book>) (root, query, builder) -> {
            List<Predicate> conditionList = new ArrayList<>();
            for (String fieldName: fieldsToSearchIn) {
                  conditionList.add(builder.like(root.get(fieldName).as(String.class), "%"+searchTerm+"%"));
            }
            return builder.or(conditionList.toArray(new Predicate[] {}));
        };
    }
}
