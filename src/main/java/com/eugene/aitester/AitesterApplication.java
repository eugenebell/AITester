package com.eugene.aitester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AitesterApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AitesterApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
		// SpringApplication.run(AitesterApplication.class, args);
	}

}
