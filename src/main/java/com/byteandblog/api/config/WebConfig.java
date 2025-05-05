package com.byteandblog.api.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${application.uploads.directory:uploads}")
	private String uploadsDirectory;

	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/api/**") .allowedOrigins("https://byteandblog.com",
	 * "https://www.byteandblog.com", "http://byteandblog.com",
	 * "http://www.byteandblog.com","http://localhost:3000") // In production,
	 * restrict to your domain .allowedMethods("GET", "POST", "PUT", "DELETE",
	 * "OPTIONS") .allowedHeaders("Authorization", "Content-Type",
	 * "X-Requested-With").allowedHeaders("*") .allowCredentials(true).maxAge(3600);
	 * }
	 */

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Expose the uploads directory as a static resource
		Path uploadsPath = Paths.get(uploadsDirectory);
		String uploadsAbsolutePath = uploadsPath.toFile().getAbsolutePath();

		registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadsAbsolutePath + "/")
				.setCachePeriod(3600) // Cache for 1 hour
				.resourceChain(true);

		System.out.println("Configured resource handler for uploads directory: " + uploadsAbsolutePath);
	}

}
