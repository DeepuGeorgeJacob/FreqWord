package com.freq.word.analyser;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "API Endpoints", version = "1.0", description = "Documentation Freq Words v1.0"))
public class AnalyserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyserApplication.class, args);
	}

}
