package ru.company.understandablepractice.dto.mapper;

import org.mapstruct.Mapper;
import ru.company.understandablepractice.dto.CustomerMeetInfoResponse;
import ru.company.understandablepractice.model.Meet;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CustomerMeetInfoMapper {

    public abstract CustomerMeetInfoResponse fromEntityToResponse(List<Meet> meets);

    int mapMeetCount(List<Meet> meets) {
        return meets.size();
    }

    LocalDate mapLastMeet(List<Meet> meets) {
        return meets.getLast().getDateMeet();
    }

    LocalDate mapNextMeet(List<Meet> meets) {
        return meets.getLast().getNextDayMeet();
    }
}
