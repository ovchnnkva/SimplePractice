package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuResponse;
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

    @Operation(summary = "Левое меню", description = "Позволяет получить левое меню текущего пользователя")
    @GetMapping("/get/{userId}")
    public LeftMenuResponse getLeftMenu(@PathVariable(name = "userId") @Parameter(name = "ID Пользователя") long userId){
        return leftMenuService.getLeftMenu(userId);
    }

}
