package ru.company.understandablepractice.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.types.ClientStatus;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.model.types.MeetingFormat;

import java.time.LocalDate;

public class CalendarSpecification {

    public static Specification<Meet> hasUser(long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Meet> hasStartDate(int year) {
        return (root, query, criteriaBuilder) -> {
             return criteriaBuilder.greaterThanOrEqualTo(root.get("dateMeet"), LocalDate.of(year, 1, 1));
        };
    }

    public static Specification<Meet> hasEndDate(int year) {
        return (root, query, criteriaBuilder) -> {

            return criteriaBuilder.lessThanOrEqualTo(root.get("dateMeet"), LocalDate.of(year, 12, 31));
        };
    }

    public static Specification<Meet> hasClientStatus(ClientStatus status) {
        return (root, query, criteriaBuilder) -> {
            if(status != null) return criteriaBuilder.equal(root.get("customer").get("clientStatus"), status);
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }

    public static Specification<Meet> hasClientType(ClientType type) {
        return (root, query, criteriaBuilder) -> {
            if (type != null) return criteriaBuilder.equal(root.get("customer").get("clientType"), type);
            else return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }

    public static Specification<Meet> hasFormat(MeetingFormat format) {
        return (root, query, criteriaBuilder) -> {
            if(format != null) return criteriaBuilder.equal(root.get("formatMeet"), format);
            else return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        };
    }
}
