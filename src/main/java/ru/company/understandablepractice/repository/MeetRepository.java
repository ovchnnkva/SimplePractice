package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.company.understandablepractice.model.Meet;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {

    @Query(value =
            "SELECT m.customer, m.dateMeet, m.startMeet, m.endMeet " +
            "FROM Meet m " +
            "WHERE m.user = :userId AND EXTRACT(year FROM m.startMeet) = :year"
    )
    List<Meet> findByUserIdAndYear(long userId, String year);
}
