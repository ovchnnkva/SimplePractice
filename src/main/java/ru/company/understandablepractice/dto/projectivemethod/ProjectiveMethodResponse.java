package ru.company.understandablepractice.dto.projectivemethod;

import lombok.Data;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.customers.CustomerResponse;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectiveMethodResponse {

    private long id;

    private MeetResponse meet;

    private CustomerResponse customer;

    private LocalDate dateCreateMethod;

    private TypeMethodResponse typeMethod;

    private List<PhotoProjectiveMethodResponse> photoProjectiveMethods;
}
