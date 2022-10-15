package com.army.ardiary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig{
    //springfox 기본 config
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Army Project API")
                .description("Army api")
                .version("0.1")
                .build();
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)//swagger 제공 기본 응답 코드. false로 설정하면 안보임
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.army.ardiary")) //api스펙이 작성되어있는 패키지(컨트롤러) 지정 RequestHandlerSelectors.any()로 선언해도 됨
                .paths(PathSelectors.any()) //path에 입력한 조건에 맞는 api를 문서화
                .build()
                .apiInfo(apiInfo());
        //아아
    }
}