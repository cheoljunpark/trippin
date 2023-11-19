package com.trippin.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

  private String version = "version 1.0";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false) // Swagger 에서 제공해주는 기본 응답 코드를 표시할 것이면 true
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(
            RestController.class))  // Controller가 들어있는 패키지. 이 경로의 하위에 있는 api만 표시됨. (RequestHandlerSelectors.any()로 설정시 모든 Controller가 보여짐)
        .paths(PathSelectors.any()) // 위 패키지 안의 api 중 지정된 path만 보여줌. (any()로 설정 시 모든 api가 보여짐)
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Trippin")
        .description("SSAFY 10기 12반 여행 프로젝트")
        .version(version)
        .contact(new Contact("정필모, 박철준", "url 입력", "email 입력"))
        .build();
  }
}