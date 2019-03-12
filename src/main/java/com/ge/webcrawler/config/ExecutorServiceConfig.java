package com.ge.webcrawler.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfig {

	@Value("${crawler.threadpool.size:5}")
	Integer threadSize;

	@Bean
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(threadSize);
	}

}
