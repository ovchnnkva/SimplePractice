package ru.company.understandablepractice.dto.projectivemethod;

import lombok.Data;
import ru.company.understandablepractice.dto.MeetResponse;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectiveMethodResponse {

    private long id;

    private MeetResponse meet;

    private LocalDate dateCreateMethod;

    private TypeMethodResponse typeMethod;

    private List<PhotoProjectiveMethodResponse> photoProjectiveMethods;
}
