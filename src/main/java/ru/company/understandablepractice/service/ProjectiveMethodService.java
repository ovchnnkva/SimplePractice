package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.mapper.ProjectiveMethodDetailsMapper;
import ru.company.understandablepractice.dto.projectivemethod.ProjectiveMethodDetailsResponse;
import ru.company.understandablepractice.model.PhotoProjectiveMethod;
import ru.company.understandablepractice.model.ProjectiveMethod;
import ru.company.understandablepractice.model.TypeMethod;
import ru.company.understandablepractice.repository.PhotoProjectiveMethodRepository;
import ru.company.understandablepractice.repository.ProjectiveMethodRepository;
import ru.company.understandablepractice.repository.TypeMethodRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectiveMethodService extends CRUDService<ProjectiveMethod>{

    private final ProjectiveMethodDetailsMapper projectiveMethodDetailsMapper;

    private final ProjectiveMethodRepository repository;
    private final TypeMethodRepository typeMethodRepository;
    private final PhotoProjectiveMethodRepository photoProjectiveMethodRepository;

    ProjectiveMethodService(ProjectiveMethodDetailsMapper projectiveMethodDetailsMapper, ProjectiveMethodRepository repository,
                            TypeMethodRepository typeMethodRepository, PhotoProjectiveMethodRepository photoProjectiveMethodRepository) {
        super(repository);
        this.projectiveMethodDetailsMapper = projectiveMethodDetailsMapper;
        this.photoProjectiveMethodRepository = photoProjectiveMethodRepository;
        this.repository = repository;
        this.typeMethodRepository = typeMethodRepository;
    }

    public Optional<List<ProjectiveMethodDetailsResponse>> findProjectiveMethodByMeetId(long meetId) {
        List<ProjectiveMethodDetailsResponse> responseList = null;

        List<ProjectiveMethod> projectiveMethods = repository.findByMeetId(meetId).orElse(null);
        if (projectiveMethods != null) {
            responseList = projectiveMethods.stream().map(response -> {
                List<TypeMethod> typeMethodResponseList = typeMethodRepository.findByProjectiveMethodId(response.getId()).orElse(null);
                List< PhotoProjectiveMethod> photoProjectiveMethodList = photoProjectiveMethodRepository.findByProjectiveMethodId(response.getId()).orElse(null);

                return projectiveMethodDetailsMapper.fromEntityToResponse(response, typeMethodResponseList, photoProjectiveMethodList);
            }).collect(Collectors.toList());
        }

        return Optional.ofNullable(responseList);
    }
}
