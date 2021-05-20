package top.hanshin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Slf4j
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {
	

	@Bean
    public Docket knife4jApiDoc() {
		log.info("===== Knife4j Config ======");
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //.description(properties.getDescription())
                        //.contact(properties.getContact())
                        //.version(properties.getVersion())
                        .build())
                //.groupName(properties.getGroupName())
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.hanshin"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

}
