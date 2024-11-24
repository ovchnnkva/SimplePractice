package ru.company.understandablepractice.dto.projectivemethod;

import lombok.Data;
import ru.company.understandablepractice.dto.MeetResponse;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectiveMethodDetailsResponse {

    private long id;

    private MeetResponse meet;

    private LocalDate dateCreateMethod;

    private TypeMethodResponse typeMethod;

    private List<PhotoProjectiveMethodResponse> photoProjectiveMethod;
}

/*
    {
        "projectiveTechnique": [{
            "meetId": "integer",
            "projectiveMethodId": "integer",
            "dateCreateMethod": "integer",
            "typeMethod": {
                "typeMethodId": "integer",
                "nameMethod": "string",
                "user_id": "integer"
            },
            "photoProjectiveMethod"[{
                "photoProjectiveMethodId": "integer",
                "photoMethod": "string",
                "dateCreatePhoto": "date"
            }]
        }]
    }
*/