package ru.company.understandablepractice.dto.projectivemethod;

import lombok.Data;
import ru.company.understandablepractice.dto.MeetResponse;

import java.time.LocalDate;

@Data
public class ProjectiveMethodResponse {

    private long id;

    private MeetResponse meet;

    private LocalDate dateCreateMethod;
}
