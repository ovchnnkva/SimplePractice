package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.company.understandablepractice.model.PhotoProjectiveMethod;

import java.util.List;
import java.util.Optional;

public interface PhotoProjectiveMethodRepository extends JpaRepository<PhotoProjectiveMethod, Long> {

    @Query(value =
        "SELECT p " +
                "FROM PhotoProjectiveMethod p " +
                "WHERE p.projectiveMethod.id = :projectiveMethodId " +
                "ORDER BY p.dateCreatePhoto DESC"
    )
    Optional<List<PhotoProjectiveMethod>> findByProjectiveMethodId(@Param("projectiveMethodId") long projectiveMethodId);
}
