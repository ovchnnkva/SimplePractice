package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuResponse;
import ru.company.understandablepractice.dto.mapper.LeftMenuUserDataMapper;
import ru.company.understandablepractice.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static ru.company.understandablepractice.utils.DateFormatUtil.formatDateToString;

@Service
@RequiredArgsConstructor
public class LeftMenuService {
    private final UserRepository userRepository;

    private final LeftMenuUserDataMapper userDataMapper;

    public Optional<LeftMenuResponse> getLeftMenu(long userId) {
        LeftMenuResponse response = null;

        //TODO: Сделать получение текущего пользователя
        var user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            response = new LeftMenuResponse();
            response.setActualTimestamp(formatDateToString(LocalDate.now()));
            response.setUserData(userDataMapper.fromEntityToResponse(user));
        }
        return Optional.ofNullable(response);
    }
}
