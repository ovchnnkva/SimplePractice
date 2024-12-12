package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.company.understandablepractice.model.PhotoProjectiveMethod;
import ru.company.understandablepractice.model.ProjectiveMethod;

import java.util.List;

public interface ProjectiveMethodRepository extends JpaRepository<ProjectiveMethod, Long> {

    @Query(value =
        "SELECT pm " +
                "FROM ProjectiveMethod pm " +
                "WHERE pm.meet.id = :meetId " +
                "ORDER BY pm.dateCreateMethod DESC"
    )
    List<ProjectiveMethod> findByMeetId(@Param("meetId")long meetId);

    @Query(value =
        "SELECT pm " +
                "FROM ProjectiveMethod pm " +
                "WHERE pm.customer.id = :customerId " +
                "ORDER BY pm.dateCreateMethod DESC"
    )
    List<ProjectiveMethod> findByCustomerId(@Param("customerId") long customerId);
}
