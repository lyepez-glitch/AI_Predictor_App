//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiPredictorApplication {
	public AiPredictorApplication() {

	}

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));
		SpringApplication.run(AiPredictorApplication.class, args);
	}
}
