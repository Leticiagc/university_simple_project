package com.ufcg.university.annotations;

import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class ProfessorOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ProfessorOperation annotation = handlerMethod.getMethodAnnotation(ProfessorOperation.class);
        if (annotation != null) {
            operation.description(annotation.description());
        }
        return operation;
    }
}
