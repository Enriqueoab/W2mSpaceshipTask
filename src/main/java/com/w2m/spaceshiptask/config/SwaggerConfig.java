package com.w2m.spaceshiptask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI customOpenAPI(
			@Value("${swagger.application-description}") String appDescription,
			@Value("${swagger.version}") String appVersion,
			@Value("${swagger.title}") String title,
			@Value("${swagger.application-url}") String web,
			@Value("${spring.application.name}") String appName) {
	   return new OpenAPI() 
			    .info(new Info()
			    		.title(title)
			    		.version(appVersion)
			    		.description(appDescription)
			    		.license(new License()
			    				.name(appName)
			    				.url(web)));
	}
	
}
