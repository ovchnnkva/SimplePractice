package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.company.understandablepractice.model.TypeMethod;

import java.util.List;
import java.util.Optional;

public interface TypeMethodRepository extends JpaRepository<TypeMethod, Long> {

    @Query(value =
        "SELECT tm " +
                "FROM TypeMethod tm " +
                "WHERE tm.projectiveMethod.id = :projectiveMethodId"
    )
    Optional<List<TypeMethod>> findByProjectiveMethodId(@Param("projectiveMethodId") long projectiveMethodId);
}
