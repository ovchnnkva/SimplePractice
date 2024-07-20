package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuClientDataResponse;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuResponse;
import ru.company.understandablepractice.dto.mapper.LeftMenuButtonMapper;
import ru.company.understandablepractice.dto.mapper.LeftMenuClientReqMapper;
import ru.company.understandablepractice.dto.mapper.LeftMenuUserDataMapper;
import ru.company.understandablepractice.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.company.understandablepractice.utils.DateFormatUtil.formatDateToString;
import static ru.company.understandablepractice.utils.HttpErrorResponse.getNotFoundResponse;

@Service
@RequiredArgsConstructor
public class LeftMenuService {
    private final UserRepository userRepository;

    private final LeftMenuUserDataMapper userDataMapper;
    private final LeftMenuButtonMapper buttonMapper;
    private final LeftMenuClientReqMapper clientReqMapper;

    public LeftMenuResponse getLeftMenu(long userId) {
        LeftMenuResponse response = new LeftMenuResponse();

        response.setActualTimestamp(formatDateToString(LocalDate.now()));

        //TODO: Сделать получение текущего пользователя

        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            response.setError(getNotFoundResponse());
        } else {
            var clientData = new LeftMenuClientDataResponse();
            clientData.setUserData(userDataMapper.fromEntityToResponse(user));
            clientData.setLeftMenuButtons(List.of(
                    buttonMapper.fromEntityToResponse("Customer icon", "Кустомеры", "Customers url"),
                    buttonMapper.fromEntityToResponse("Calendar icon", "Календарь", "Calendar url"),
                    buttonMapper.fromEntityToResponse("Tests icon", "Тестиксы", "Tests url")
            ));
            clientData.setClientRequests(List.of(
                    clientReqMapper.fromEntityToResponse("Олежка", "bimbim.com/bombom")
            ));
            response.setClientData(clientData);
        }
        return response;
    }
}
