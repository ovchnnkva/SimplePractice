package ru.company.understandablepractice.dto.mapper.questionnaire;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.company.understandablepractice.dto.questionnaire.ClientResultMinResponse;
import ru.company.understandablepractice.dto.questionnaire.ClientResultRequest;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.questionnaire.ClientResult;

@Mapper(componentModel = "spring")
public abstract class ClientResultMapper {

    @Mapping(target = "questionnaireTitle", expression = "java(mapQuestionnaireTitle(entity))")
    @Mapping(target = "test", expression = "java(mapIsTest(entity))")
    public abstract ClientResultMinResponse fromEntityToResponse(ClientResult entity);
//
//    @Mapping(target = "customer", expression = "java(mapCustomer(request))")
//    public abstract ClientResult fromRequestToEntity(ClientResultRequest request);

    String mapQuestionnaireTitle(ClientResult entity) {
        return entity.getQuestionnaire().getTitle();
    }

    boolean mapIsTest(ClientResult entity) {
        return entity.getQuestionnaire().isTest();
    }

    Customer mapCustomer(ClientResultRequest request) {
        return new Customer(request.getCustomerId());
    }
}
