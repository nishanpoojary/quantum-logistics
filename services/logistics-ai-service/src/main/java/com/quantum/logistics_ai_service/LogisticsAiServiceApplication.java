package com.quantum.logistics_ai_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {org.springframework.ai.model.openai.autoconfigure.OpenAiAudioSpeechAutoConfiguration.class, org.springframework.ai.model.openai.autoconfigure.OpenAiAudioTranscriptionAutoConfiguration.class})
public class LogisticsAiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticsAiServiceApplication.class, args);
	}

}
