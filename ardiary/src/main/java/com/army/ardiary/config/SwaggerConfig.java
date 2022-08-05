package com.army.ardiary.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
//sprngdoc기본 config
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("army")
                .packagesToScan("com.army.ardiary")
                //.pathsToMatch("/api/**")
                .build();
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("AR Diary API")
                        .description("한이음 아르미 팀의 백 api 문서")
                        .version("version 0"));
    }
}
//springfox 기본 config
/*    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)//swagger 제공 기본 응답 코드. false로 설정하면 안보임
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.army.ardiary.controller")) //api스펙이 작성되어있는 패키지(컨트롤러) 지정 RequestHandlerSelectors.any() 로 선언해도 됨
                .paths(PathSelectors.any()) //path에 입력한 조건에 맞는 api를 문서화
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Army Swagger")
                .description("Army swagger config")
                .version("3.0.0")
                .build();
    }*/
