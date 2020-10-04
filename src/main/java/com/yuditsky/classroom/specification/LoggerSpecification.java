package com.yuditsky.classroom.specification;

import com.yuditsky.classroom.entity.LoggerEntity;
import com.yuditsky.classroom.model.Action;
import com.yuditsky.classroom.parser.CriteriaValueParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggerSpecification implements Specification<LoggerEntity> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<LoggerEntity> root, CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation().equalsIgnoreCase(">") && isClass(LocalDateTime.class, root)) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()),
                    CriteriaValueParser.toLocalDateTime(criteria));
        } else if (criteria.getOperation().equalsIgnoreCase("<") && isClass(LocalDateTime.class, root)) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(criteria.getKey()),
                    CriteriaValueParser.toLocalDateTime(criteria));
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (isClass(Action.class, root)) {
                return criteriaBuilder.equal(
                        root.get(criteria.getKey()),
                        CriteriaValueParser.toAction(criteria));
            } else {
                return criteriaBuilder.equal(
                        root.get(criteria.getKey()),
                        criteria.getValue().toString());
            }
        }
        return null;
    }

    private boolean isClass(Class clazz, Root<LoggerEntity> root) {
        return root.get(criteria.getKey()).getJavaType() == clazz;
    }
}
