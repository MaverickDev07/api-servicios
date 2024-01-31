/*package org.allivia.api.alliviaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis( RequestHandlerSelectors.basePackage("org/allivia/api/alliviaapi/controllers") )
                // .paths(PathSelectors.ant("/allivia-app/api/v1/**"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Documentación API REST Allivia",
                "Pruebe y ejecute los Servicios REST",
                "V01",
                "terminos",
                new Contact("GaryDav","/", "gary.2810.dav@gmail.com"),
                "Licencia del API",
                "licencia",
                Collections.emptyList()
        );
    }
}
*/