package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.dto.NotificationResponse;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuResponse;
import ru.company.understandablepractice.dto.mapper.CustomerMapper;
import ru.company.understandablepractice.dto.mapper.LeftMenuUserDataMapper;
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
    private final CustomerMapper customerMapper;

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

    public List<NotificationResponse> getNotification(long userId) {
        List<CustomerResponse> customers = getNewCustomersByUser(userId);
        return customers.stream().map(customerResponse -> {
           var notification = new NotificationResponse();

           notification.setCustomerFullName(customerResponse.getFullName());
           notification.setDateFirstRequest(customerResponse.getDateFirstRequest());

           return notification;
        }).collect(Collectors.toList());
    }

    private List<CustomerResponse> getNewCustomersByUser(long userId) {
        List<Customer> customers = customerRepository.findNewCustomerByUserAndStatus(userId, ClientStatus.REQUEST).orElse(null);
        if (customers != null) {
            return customers.stream().map(customerMapper::fromEntityToResponse).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
