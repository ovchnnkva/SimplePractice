package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.company.understandablepractice.model.ProjectiveMethod;

import java.util.List;
import java.util.Optional;

public interface ProjectiveMethodRepository extends JpaRepository<ProjectiveMethod, Long> {

    @Query(value =
        "SELECT pm " +
                "FROM ProjectiveMethod pm " +
                "WHERE pm.meet.id = :meetId " +
                "ORDER BY pm.dateCreateMethod DESC"
    )
    Optional<List<ProjectiveMethod>> findByMeetId(@Param("meetId")long meetId);
}
