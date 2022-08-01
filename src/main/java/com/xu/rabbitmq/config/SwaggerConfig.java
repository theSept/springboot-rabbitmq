package com.xu.rabbitmq.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * User: 彼暗
 * Date: 2022-06-29
 * Time: 22:28
 * Versions: 1.0.0
 */
@Configuration
@EnableKnife4j  // 开启knife4j的增强功能
@EnableSwagger2 // 开启swagger2
public class SwaggerConfig {

     @Bean
    public Docket docket(ApiInfo apiInfo, Environment environment){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                // enable 是否启动swagger
                .enable(true)
                .groupName("xu")
                .select()
                // withMethodAnnotation:扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.xu.schoolmanage.controller"))
                .build()
                ;
    }

    // 配置Swagger首页信息
    @Bean
    public ApiInfo apiInfo(){
        // 作者信息
        Contact DEFAULT_CONTACT = new Contact("程序猿", "", "3232129642@qq.com");
        return new ApiInfo(
                "彼暗滴SwaggerAPI文档",
                "没有笨人，只有懒人",
                "1.0",
                "urn:tos",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}