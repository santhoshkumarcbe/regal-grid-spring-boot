package com.regalgrid.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RegalgridApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegalgridApplication.class, args);
		System.out.println("start");
	}

}
