package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.understandablepractice.dto.NotificationListResponse;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuUserDataResponse;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.service.LeftMenuService;

@Tag(
        name = "Left Menu Bar"
)
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/General/leftMenu")
public class LeftMenuController {

    private final LeftMenuService leftMenuService;
    private final HttpServletRequest request;
    private final JwtService jwtService;

    @Operation(summary = "Информация о пользователе", description = "Позволяет получить ФИО, почту и аватарку пользователя")
    @GetMapping("/userInfo")
    public ResponseEntity<LeftMenuUserDataResponse> getLeftMenuUser() {
        Long userId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        log.info("auth user {}", userId);
        return leftMenuService.getLeftMenuUserData(userId)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Оповещения",
            description = "Позволяет получить список новых клиентов. applicationFormStatus - статус анкеты. 0 - не создана, 1 - создана, 2 - заполнена, 3 - токен просрочен")
    @GetMapping("/notification")
    public ResponseEntity<NotificationListResponse> getNotification() {
        Long userId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
        log.info("auth user {}", userId);
        return leftMenuService.getNotification(userId)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
