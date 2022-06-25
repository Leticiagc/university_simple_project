package com.ufcg.university.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Operation(summary = "Get Professor By Id",
        responses = {
                @ApiResponse(responseCode = "200", description = "It's Ok"),
                @ApiResponse(responseCode = "204", description = "Professor Not Found")
        },
        extensions = {
                @Extension(
                        name = "documentation",
                        properties = {
                                @ExtensionProperty(
                                        name = "link",
                                        value = "mylink.com"
                                )
                        }
                )
        }
)
public @interface ProfessorGetByIDOperation {
}