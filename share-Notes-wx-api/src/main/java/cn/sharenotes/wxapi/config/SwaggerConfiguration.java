package cn.sharenotes.wxapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger configuration.
 *
 * @author kiwi
 */
@EnableSwagger2
@Configuration
@Slf4j
public class SwaggerConfiguration {


    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("cn.sharenotes.wxapi"))
                .paths(PathSelectors.any()).build();

    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //页面标题
                .title("ShareNotes API")
                //创建人
                .contact(new Contact("kiwi","http://www.kiwi1.cn","805344479@qq,com"))
                //版本号
                .version("0.0.1")
                //描述
                .description("API description")
                .build();
    }

}
