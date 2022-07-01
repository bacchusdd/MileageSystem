package com.example.triple.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
/**
 * Swagger-Ui 사용을 위한 설정을 담당하는 클래스
 * http://localhost:8080/swagger-ui.html
 */

public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("triple-mileage")
                .description("Description")
                .version("1.0")
                .build();
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");

        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produce = new HashSet<>();
        produce.add("application/json;charset=UTF-8");

        return produce;
    }

    @Bean
    public Docket allApi() {
        return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.triple"))
                //.paths(PathSelectors.ant("/events/**"))
                .paths(PathSelectors.any())
                .build();
    }

}