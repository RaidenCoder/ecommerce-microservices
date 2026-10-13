package com.example.ecommerce.discovery_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.micrometer.metrics.autoconfigure.system.SystemMetricsAutoConfiguration;
import org.springframework.boot.tomcat.autoconfigure.metrics.TomcatMetricsAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication(exclude = {
		SystemMetricsAutoConfiguration.class,
		TomcatMetricsAutoConfiguration.class
})
@EnableEurekaServer
public class DiscoveryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}
}
