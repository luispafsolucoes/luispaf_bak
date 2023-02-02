package com.projeto.luispaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class LuispafApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuispafApplication.class, args);
	}

}
