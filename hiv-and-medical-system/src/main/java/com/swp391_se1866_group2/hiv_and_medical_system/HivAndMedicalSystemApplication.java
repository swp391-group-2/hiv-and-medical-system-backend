package com.swp391_se1866_group2.hiv_and_medical_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HivAndMedicalSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HivAndMedicalSystemApplication.class, args);
	}

}
