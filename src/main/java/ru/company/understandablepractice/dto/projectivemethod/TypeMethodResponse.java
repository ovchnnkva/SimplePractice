package ru.company.understandablepractice.dto.projectivemethod;

import lombok.Data;
import ru.company.understandablepractice.dto.UserResponse;

@Data
public class TypeMethodResponse {

    private long id;

    private String nameMethod;

    private UserResponse user;
}
