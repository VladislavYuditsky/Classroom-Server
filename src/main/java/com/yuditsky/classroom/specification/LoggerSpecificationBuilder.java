package com.yuditsky.classroom.specification;

import com.yuditsky.classroom.entity.LoggerEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoggerSpecificationBuilder {
    private final List<SearchCriteria> criteria;

    public LoggerSpecificationBuilder() {
        criteria = new ArrayList<>();
    }

    public LoggerSpecificationBuilder with(String key, String operation, Object value) {
        criteria.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<LoggerEntity> build() {
        if (criteria.size() == 0) {
            return null;
        }

        List<Specification> specifications = criteria.stream()
                .map(LoggerSpecification::new)
                .collect(Collectors.toList());

        Specification result = specifications.get(0);

        for (int i = 1; i < criteria.size(); i++) {
            result = Specification.where(result).and(specifications.get(i));
        }

        return result;
    }
}
