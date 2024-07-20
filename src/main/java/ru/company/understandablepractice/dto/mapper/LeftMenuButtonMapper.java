package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.leftmenu.LeftMenuButtonResponse;

@Mapper(componentModel = "spring")
public abstract class LeftMenuButtonMapper {

    @Mapping(target = "icon", expression = "java(mapIcon(icon))")
    @Mapping(target = "title", expression = "java(mapTitle(title))")
    @Mapping(target = "link", expression = "java(mapLink(link))")
    public abstract LeftMenuButtonResponse fromEntityToResponse(String icon, String title, String link);

    String mapIcon(String icon){
        return "Btn icon: " + icon;
    }

    String mapTitle(String title){
        return "Btn title: " + title;
    }

    String mapLink(String link){
        return "Btn link: " + link;
    }
}
