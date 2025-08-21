package com.adhunikkethi.adhunnikkethi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.adhunikkethi.adhunnikkethi.Respository")
public class AdhunnikkethiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdhunnikkethiApplication.class, args);
	}

}
