package ru.company.understandablepractice.dto.projectivemethod;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PhotoProjectiveMethodResponse {

    private long id;

    private Long projectiveMethod;

    private String photoMethod;

    private LocalDate dateCreatePhoto;
}
