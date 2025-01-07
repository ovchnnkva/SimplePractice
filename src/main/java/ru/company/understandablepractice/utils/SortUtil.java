package ru.company.understandablepractice.utils;

import org.springframework.data.domain.Sort;

import java.util.Map;

public class SortUtil {
    public static final String DESC = "desc";

    public static Sort createSort(Map<String, String> sortedFields) {
        return Sort.by(
                sortedFields
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                        .map(entry ->
                        DESC.equalsIgnoreCase(entry.getValue())
                                ? Sort.Order.desc(entry.getKey())
                                : Sort.Order.asc(entry.getKey())
                        ).toList()
        );
    }
}


