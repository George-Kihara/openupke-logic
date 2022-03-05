package com.labs.openupke.config

import com.google.api.client.util.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.labs.openupke"))
                .paths(PathSelectors.any())
                .build()
                .enable(enableSwaggerPlugin)
                .apiInfo(getApiInfo())
    }

    @Value("\${enable.swagger.plugin:true}")
    private val enableSwaggerPlugin = false

    fun getApiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Openupke CRUD service")
                .description("CRUD API Documentation")
                .version("1.0.0")
                .build()
    }
}