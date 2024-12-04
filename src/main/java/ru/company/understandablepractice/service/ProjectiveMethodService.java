package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.mapper.ProjectiveMethodMapper;
import ru.company.understandablepractice.dto.projectivemethod.ProjectiveMethodResponse;
import ru.company.understandablepractice.model.ProjectiveMethod;
import ru.company.understandablepractice.repository.ProjectiveMethodRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectiveMethodService extends CRUDService<ProjectiveMethod>{

    private final ProjectiveMethodRepository repository;
    private final ProjectiveMethodMapper projectiveMethodMapper;
    private final TypeMethodService typeMethodService;

    ProjectiveMethodService(ProjectiveMethodRepository repository, ProjectiveMethodMapper projectiveMethodMapper, TypeMethodService typeMethodService) {
        super(repository);
        this.repository = repository;
        this.projectiveMethodMapper = projectiveMethodMapper;
        this.typeMethodService = typeMethodService;
    }

    @Override
    public Optional<ProjectiveMethod> create(ProjectiveMethod entity) throws Exception {
        if (entity.getTypeMethod() != null && entity.getTypeMethod().getId() == 0) {
            entity.getTypeMethod().setId(typeMethodService.saveTypeMethod(entity.getTypeMethod()));
        }

        return super.create(entity);
    }

    public List<ProjectiveMethod> findProjectiveMethodByMeetId(long meetId) {
        return repository.findByMeetId(meetId);
    }
}
