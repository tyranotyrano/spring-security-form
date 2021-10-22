package com.tyranotyrano.springsecurityform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringSecurityFormApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityFormApplication.class, args);
	}

}
