package com.batch.readerwriter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ReaderwriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaderwriterApplication.class, args);
	}

}
