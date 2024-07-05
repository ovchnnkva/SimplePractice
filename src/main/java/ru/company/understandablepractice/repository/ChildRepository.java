package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
}
