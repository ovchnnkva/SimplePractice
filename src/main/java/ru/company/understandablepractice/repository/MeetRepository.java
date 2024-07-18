package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.Meet;

import java.util.List;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Long> {

    @Query(value =
            "SELECT m " +
            "FROM Meet m " +
            "WHERE m.user.id = :userId AND EXTRACT(year FROM m.dateMeet) = :year"
    )
    List<Meet> findByUserIdAndYear(long userId, String year);
}
