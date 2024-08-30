package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.NotificationResponse;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuResponse;
import ru.company.understandablepractice.dto.mapper.LeftMenuUserDataMapper;
import ru.company.understandablepractice.dto.mapper.NotificationMapper;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.types.ClientStatus;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.company.understandablepractice.utils.DateFormatUtil.formatDateToString;

@Service
@RequiredArgsConstructor
public class LeftMenuService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    private final LeftMenuUserDataMapper userDataMapper;
    private final NotificationMapper notificationMapper;

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

    public Optional<List<NotificationResponse>> getNotification(long userId) {
        List<NotificationResponse> response = null;

        List<Customer> customers = customerRepository.findNewCustomerByUserAndStatus(userId, ClientStatus.REQUEST).orElse(new ArrayList<>());
        response = customers.stream().map(notificationMapper::fromEntityToResponse).collect(Collectors.toList());

        return Optional.of(response);
    }
}
