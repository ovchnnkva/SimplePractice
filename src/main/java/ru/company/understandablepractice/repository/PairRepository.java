package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.understandablepractice.model.Pair;

public interface PairRepository extends JpaRepository<Pair, Long> {
}
