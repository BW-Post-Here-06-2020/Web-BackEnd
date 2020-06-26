package com.lambdaschool.subredditpredictor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @EnableJpaAuditing
@SpringBootApplication
public class SubredditPredictorApplication {
	// for local without env variables
	public static void main(String[] args) {
		SpringApplication.run(SubredditPredictorApplication.class, args);
	}

	// @Autowired
	// private static Environment env;
	//
	// private static boolean stop = false;
	//
	// private static void checkEnvironmentVariable(String envVar) {
	// 	if (System.getenv(envVar) == null) {
	// 		stop = true;
	// 	}
	// }
	//
	// public static void main(String[] args) {
	// 	checkEnvironmentVariable("OAUTHCLIENTID");
	// 	checkEnvironmentVariable("OAUTHCLIENTSECRET");
	//
	// 	if (!stop) {
	// 		SpringApplication.run(SubredditPredictorApplication.class, args);
	// 	}
	// }

}
