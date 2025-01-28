package ru.company.understandablepractice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.User;

@Data
public class UserDiplomasResponse {

    private long id;

    private String photoDiploma;
}
