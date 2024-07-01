package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
