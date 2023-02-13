package com.projeto.luispaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@SpringBootApplication
@EnableScheduling
@Component	
public class LuispafApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuispafApplication.class, args);
	}

}
