package com.gen.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger
 */
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
public class WebConfig extends WebMvcConfigurationSupport {

    final String BASE_PACKAGE = "com.gen.mcn.service";
    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket helloDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                //注册整体api信息
                .apiInfo(apiInfo())
                //swagger功能是否启用
                .enable(enableSwagger)
                .select()
                //指定扫描的包
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Api Info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("公共项目模块")
                .description("包含数据字典、通用接口封装等")
                .contact(new Contact("JOE", "", ""))
                .version("0.0.1")
                .build();
    }

    /**
     * 解决swagger-ui.html 404无法访问的问题
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger无法访问
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
