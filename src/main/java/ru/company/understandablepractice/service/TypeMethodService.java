package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.mapper.TypeMethodMapper;
import ru.company.understandablepractice.dto.projectivemethod.TypeMethodResponse;
import ru.company.understandablepractice.model.TypeMethod;
import ru.company.understandablepractice.repository.TypeMethodRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TypeMethodService extends CRUDService<TypeMethod> {
    private final TypeMethodRepository repository;
    private final TypeMethodMapper mapper;
    TypeMethodService(TypeMethodRepository repository, TypeMethodMapper mapper) {
        super(repository);
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<List<TypeMethodResponse>> findAllByUserId(long userId) {
        List<TypeMethodResponse> response = null;
        List<TypeMethod> typeMethodList = repository.findByUserId(userId).orElse(null);

        if (typeMethodList != null){
            response = typeMethodList.stream().map(mapper::fromEntityToResponse).collect(Collectors.toList());
        }

        return Optional.ofNullable(response);
    }
}
