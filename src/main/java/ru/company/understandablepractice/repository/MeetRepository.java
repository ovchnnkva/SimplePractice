package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.understandablepractice.model.Meet;

public interface MeetRepository extends JpaRepository<Meet, Long> {
}
