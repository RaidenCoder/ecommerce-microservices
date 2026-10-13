package com.example.ecommerce.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.micrometer.metrics.autoconfigure.system.SystemMetricsAutoConfiguration;
import org.springframework.boot.tomcat.autoconfigure.metrics.TomcatMetricsAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {
		SystemMetricsAutoConfiguration.class,
		TomcatMetricsAutoConfiguration.class
})
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
