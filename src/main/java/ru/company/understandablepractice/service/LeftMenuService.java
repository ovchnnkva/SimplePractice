package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.NotificationListResponse;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuUserDataResponse;
import ru.company.understandablepractice.dto.mapper.LeftMenuUserDataMapper;
import ru.company.understandablepractice.dto.mapper.NotificationMapper;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.types.ClientStatus;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LeftMenuService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    private final LeftMenuUserDataMapper userDataMapper;
    private final NotificationMapper notificationMapper;

    public Optional<LeftMenuUserDataResponse> getLeftMenuUserData(long userId) {
        LeftMenuUserDataResponse response = null;

        var user = userRepository.findById(userId).orElse(null);
        if (user != null){
            response = userDataMapper.fromEntityToResponse(user);
        }

        return Optional.ofNullable(response);
    }

    public Optional<NotificationListResponse> getNotification(long userId) {
        NotificationListResponse response = null;

        List<Customer> customers = customerRepository.findNewCustomerByUserAndStatus(userId, ClientStatus.REQUEST).orElse(null);
        if (customers != null){
            response = new NotificationListResponse();
            response.setNotificationResponseList(customers.stream().map(notificationMapper::fromEntityToResponse).collect(Collectors.toList()));
            response.setCount(response.getNotificationResponseList().size());
        }

        return Optional.ofNullable(response);
    }
}
