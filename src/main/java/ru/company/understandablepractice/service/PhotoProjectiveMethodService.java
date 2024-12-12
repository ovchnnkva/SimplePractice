package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.PhotoProjectiveMethod;
import ru.company.understandablepractice.repository.PhotoProjectiveMethodRepository;

import java.util.List;

@Slf4j
@Service
public class PhotoProjectiveMethodService {

    private final PhotoProjectiveMethodRepository photoProjectiveMethodRepository;

    public PhotoProjectiveMethodService(PhotoProjectiveMethodRepository photoProjectiveMethodRepository) {
        this.photoProjectiveMethodRepository = photoProjectiveMethodRepository;
    }

    public List<PhotoProjectiveMethod> findPhotosByMethodType(long typeMethodId) {
        return photoProjectiveMethodRepository.findPhotosByTypeMethodId(typeMethodId);
    }
}
