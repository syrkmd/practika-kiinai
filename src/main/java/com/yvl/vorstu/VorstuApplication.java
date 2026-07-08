package com.yvl.vorstu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VorstuApplication {

	public static void main(String[] args) {
		SpringApplication.run(VorstuApplication.class, args);
	}

}
