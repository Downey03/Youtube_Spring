package com.youtube.Youtube;

import com.google.api.client.http.HttpStatusCodes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Set;
import java.util.TreeSet;

@SpringBootApplication
public class YoutubeApplication {


	public static void main(String[] args) {


		SpringApplication.run(YoutubeApplication.class, args);
	}

}
