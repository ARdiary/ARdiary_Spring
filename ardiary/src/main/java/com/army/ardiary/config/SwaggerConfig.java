package com.army.ardiary.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
//import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
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
//prngdoc기본 config
/*    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("타이틀 입력")
                .version(springdocVersion)
                .description("API에 대한 설명 부분");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }*/

//springfox 기본 config
private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            .title("Army Swagger")
            .description("Army swagger config")
            .version("3.0.0")
            .build();
}
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)//swagger 제공 기본 응답 코드. false로 설정하면 안보임
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.army.ardiary")) //api스펙이 작성되어있는 패키지(컨트롤러) 지정 RequestHandlerSelectors.any() 로 선언해도 됨
                .paths(PathSelectors.any()) //path에 입력한 조건에 맞는 api를 문서화
                .build()
                .apiInfo(apiInfo());
    }

}

