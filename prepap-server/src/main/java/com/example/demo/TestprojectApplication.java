package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TestprojectApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TestprojectApplication.class, args);
	}
	
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	 return builder.sources(TestprojectApplication.class);
	 }
	 
	 // hola amigo igen
	 // hej igen 
	 
	 //Syns den här?
	 
}
