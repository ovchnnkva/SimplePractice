package ru.company.understandablepractice.utils;

import org.springframework.http.HttpStatus;
import ru.company.understandablepractice.constant.HttpErrorDisplayMessage;
import ru.company.understandablepractice.dto.ErrorResponse;


public class HttpErrorResponse {

    public static ErrorResponse getNotFoundResponse() {
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .displayMessage(HttpErrorDisplayMessage.NOT_FOUND)
                .build();
    }
}
