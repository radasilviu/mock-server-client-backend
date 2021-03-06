package com.fortech.mockapp.configuration.model.specification;

import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SearchSpec {
    public static <T> Specification<T> hasSearchTermInWantedFields(ArrayList<String> fieldsToSearchIn, String searchTerm) {
        return (Specification<T>) (root, query, builder) -> {
            List<Predicate> conditionList = new ArrayList<>();
            for (String fieldName: fieldsToSearchIn) {
//                if()
                  conditionList.add(builder.like(root.get(fieldName).as(String.class), "%"+searchTerm+"%"));
            }
            return builder.or(conditionList.toArray(new Predicate[] {}));
        };
    }
}
