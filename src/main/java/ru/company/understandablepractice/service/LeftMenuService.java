package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuResponse;
import ru.company.understandablepractice.dto.mapper.LeftMenuUserDataMapper;
import ru.company.understandablepractice.repository.UserRepository;

import java.time.LocalDate;

import static ru.company.understandablepractice.utils.DateFormatUtil.formatDateToString;
import static ru.company.understandablepractice.utils.HttpErrorResponse.getNotFoundResponse;

@Service
@RequiredArgsConstructor
public class LeftMenuService {
    private final UserRepository userRepository;

    private final LeftMenuUserDataMapper userDataMapper;

    public LeftMenuResponse getLeftMenu(long userId) {
        LeftMenuResponse response = new LeftMenuResponse();

        response.setActualTimestamp(formatDateToString(LocalDate.now()));

        //TODO: Сделать получение текущего пользователя

        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            response.setError(getNotFoundResponse());
        } else {
            response.setUserData(userDataMapper.fromEntityToResponse(user));
        }
        return response;
    }
}
