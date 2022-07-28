package com.security.security20220721.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;
import java.util.stream.Collectors;

//访问地址：http://localhost:8081/swagger-ui.html
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI  //访问地址：http://localhost:8081/doc.html
//@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
//@ComponentScan(basePackages = "com.wy.demo",excludeFilters = @ComponentScan.Filter(ApiIgnore.class))
public class SwaggerConfiguration {

    private static final String MODEL_REF="string";
    private static final String PARAMTER_TYPE="header";

    @Bean
    public Docket customDocket(){
        ApiInfo ai = new ApiInfo("健康 长寿", "健康 长寿", "1.0.0", "",
                new Contact("wy", "", ""), "", "", new ArrayList<>());
        Set<String> s = new HashSet<>();

        List<Parameter> headers = Arrays.asList("token", "customerNo", "requestId", "User-Agent-Openid")
                .stream().map(header->new ParameterBuilder().name(header).modelRef(new ModelRef(MODEL_REF)).parameterType(PARAMTER_TYPE).build())
                .collect(Collectors.toList());
        s.add(MediaType.APPLICATION_JSON_VALUE);

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.security.security20220721"))
                .build()
                .produces(s)
                .globalOperationParameters(headers)
                .apiInfo(ai);
    }


 /*   @Bean
    public Docket createRestApi() {
        System.out.println("======  SWAGGER CONFIG  ======");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.wy.demo"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xxxx RESTful APIs")
                .description("快速上手,快速开发,快速交接")
                .contact("xxxx")
                .version("2.0.0")
                .build();
    }*/

}
