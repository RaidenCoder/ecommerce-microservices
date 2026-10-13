package com.example.ecommerce.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.micrometer.metrics.autoconfigure.system.SystemMetricsAutoConfiguration;

@SpringBootApplication(exclude = {
		SystemMetricsAutoConfiguration.class
})
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}