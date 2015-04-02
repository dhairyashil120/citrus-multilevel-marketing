package com.citruspay.mlm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan(basePackages = { "com.citruspay.mlm"})
@PropertySource(value = { "classpath:META-INF/spring/application.properties", "classpath:META-INF/spring/local.properties", "file:${citruspay.config.dir}/application.properties"}, ignoreResourceNotFound = true)
@EnableAsync
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableConfigurationProperties
@EnableAspectJAutoProxy
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
