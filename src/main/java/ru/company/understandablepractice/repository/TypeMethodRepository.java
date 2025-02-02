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
                "WHERE tm.user.id = :userId"
    )
    Optional<List<TypeMethod>> findByUserId(@Param("userId") long userId);

    @Query(value =
        "SELECT tm " +
                "FROM TypeMethod tm " +
                "INNER JOIN ProjectiveMethod pm " +
                "ON pm.typeMethod.id = tm.id " +
                "WHERE pm.customer.id = :customerId " +
                "GROUP BY tm.id "
    )
    List<TypeMethod> findByCustomerId(@Param("customerId") long customerId);
}
