package com.ufcg.university.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;

@Configuration
public class SwaggerConfig {
	
	private static final String SECURITY_SCHEME_NAME = "Bearer Token";
	
	@Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                		.contact(new Contact()
                				.email("ufcg@email.com")
                				.name("UFCG")
                				.url("https://portal.ufcg.edu.br/"))
                		.title("University API")
                        .description("University APP")
                        .version("v0.0.1")
                        .license(new License()
                        		.name("Apache 2.0")
                        		.url("http://springdoc.org")
                        ))
                .externalDocs(new ExternalDocumentation()
                        .description("University App Documentation")
                        .url("https://documentationlink.com.br"))
                .components(
                        new Components()
                        	.addSecuritySchemes(SECURITY_SCHEME_NAME , 
                        			new SecurityScheme()
                        				.name("Authorization")
                        				.type(SecurityScheme.Type.HTTP)
                        				.scheme("bearer")
                        				.bearerFormat("JWT")
                        				.in(In.HEADER)
                        				.description("JWT Authorization Header Using The Bearer Scheme")
                        	)
                );
    }
}
