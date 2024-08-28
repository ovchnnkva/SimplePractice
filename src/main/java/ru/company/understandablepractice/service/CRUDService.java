package ru.company.understandablepractice.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.understandablepractice.model.Customer;

import java.util.Optional;

public class CRUDService<T> {
    protected JpaRepository<T, Long> repository;

    CRUDService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }


    public Optional<T> getById(long id) {
        return repository.findById(id);
    }

    public Optional<T> create(T entity) {
        return Optional.of(repository.save(entity));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
