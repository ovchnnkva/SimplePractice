package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.CustomerMeetInfoResponse;
import ru.company.understandablepractice.model.Meet;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CustomerMeetInfoMapper {

    @Mapping(target = "countMeet", expression = "java(mapMeetCount(meets))")
    @Mapping(target = "lastMeetDate", expression = "java(mapLastMeet(meets))")
    @Mapping(target = "nextMeetDate", expression = "java(mapNextMeet(meets))")
    public abstract CustomerMeetInfoResponse fromEntityToResponse(Long customerId, List<Meet> meets);

    int mapMeetCount(List<Meet> meets) {
        return meets.size();
    }

    LocalDate mapLastMeet(List<Meet> meets) {
        return meets != null && !meets.isEmpty() ? meets.get(meets.size() - 1).getDateMeet() : null;
    }

    LocalDate mapNextMeet(List<Meet> meets) {
        return meets != null && !meets.isEmpty() ? meets.get(meets.size() - 1).getNextDayMeet() : null;
    }
}
