package com.whisperspace.Whisperspace_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WhisperspaceBackendApplication {

	public static void main(String[] args) {
		var ctx = SpringApplication.run(WhisperspaceBackendApplication.class, args);
		System.out.println(
				ctx.getEnvironment().getProperty("spring.data.mongodb.uri")
		);
		System.out.println("ACTIVE PROFILES:");
		for (String p : ctx.getEnvironment().getActiveProfiles()) {
			System.out.println(p);
		}
	}

}
