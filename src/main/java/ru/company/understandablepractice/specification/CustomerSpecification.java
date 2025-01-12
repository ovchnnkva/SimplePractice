package ru.company.understandablepractice.specification;

import ch.qos.logback.core.util.StringUtil;
import jakarta.persistence.FetchType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Meet;

import java.util.List;

public class CustomerSpecification {

    public static Specification<Customer> sortByMeetsSize(String order) {
            return (root, query, criteriaBuilder) -> {

                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Meet> meetRoot = subquery.from(Meet.class);

                if ("desc".equalsIgnoreCase(order)) {
                    subquery.select(criteriaBuilder.count(meetRoot))
                            .where(criteriaBuilder.equal(meetRoot.get("customer"), root));
                    query.orderBy(criteriaBuilder.desc(subquery));
                } else if ("asc".equalsIgnoreCase(order)) {
                    subquery.select(criteriaBuilder.count(meetRoot))
                            .where(criteriaBuilder.equal(meetRoot.get("customer"), root));
                    query.orderBy(criteriaBuilder.asc(subquery));
                }

                return null;
            };
    }

    public static Specification<Customer> sortByDateMeet(String order) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Meet> meetRoot = subquery.from(Meet.class);

            if ("desc".equalsIgnoreCase(order)) {
                subquery.select(meetRoot.get("dateMeet"))
                        .where(criteriaBuilder.equal(meetRoot.get("customer"), root));
                query.orderBy(criteriaBuilder.desc(subquery));
            } else if ("asc".equalsIgnoreCase(order)) {
                subquery.select(meetRoot.get("dateMeet"))
                        .where(criteriaBuilder.equal(meetRoot.get("customer"), root));
                query.orderBy(criteriaBuilder.asc(subquery));
            }
            return query.getRestriction();
        };
    }

    public static Specification<Customer> containingFullName(String name) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + StringUtil.nullStringToEmpty(name).toLowerCase() + "%");
    }

    public static Specification<Customer> hasUser(long userId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("user").get("id"), userId);
    }
}


