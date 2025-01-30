package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.company.understandablepractice.model.PhotoProjectiveMethod;

import java.util.List;

public interface PhotoProjectiveMethodRepository extends JpaRepository<PhotoProjectiveMethod, Long> {


    @Query(value =
            "SELECT ppm " +
                    "FROM PhotoProjectiveMethod ppm " +
                    "INNER JOIN ProjectiveMethod pm " +
                    "ON pm.id = ppm.projectiveMethod " +
                    "WHERE pm.typeMethod.id = :typeMethodId " +
                    "ORDER BY ppm.dateCreatePhoto DESC"
    )
    List<PhotoProjectiveMethod> findPhotosByTypeMethodId(@Param("typeMethodId") long typeMethodId);
}
