package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.Meet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Long> {

    @Query(value =
            "SELECT m " +
                    "FROM Meet m " +
                    "WHERE m.user.id = :userId AND EXTRACT(year FROM m.dateMeet) = :year"
    )
    List<Meet> findByUserIdAndYear(long userId, String year);

    @Query(value =
            "SELECT m.dateMeet " +
            "FROM Meet m " +
            "WHERE m.customer.id = :customerId " +
            "ORDER BY m.dateMeet DESC")
    Optional<List<LocalDate>> findClientDateMeet(@Param("customerId") long customerId);

    @Query(value =
            "SELECT m " +
                    "FROM Meet m " +
                    "WHERE m.customer.id = :customerId")
    Optional<List<Meet>> findMeetByCustomerId(@Param("customerId") long customerId);

    @Query(value =
            "SELECT m " +
                    "FROM Meet m " +
                    "WHERE m.customer.id =:customerId AND m.user.id =:userId " +
                    "ORDER BY m.dateMeet DESC " +
                    "OFFSET :offset ROWS " +
                    "FETCH NEXT :limit ROWS ONLY")
    List<Meet> findByUserIdAndCustomerId(@Param("userId") long userId, @Param("customerId") long customerId, @Param("offset") long offset, @Param("limit") long limit);
}
