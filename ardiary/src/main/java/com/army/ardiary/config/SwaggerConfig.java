package com.army.ardiary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
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
    }
}
