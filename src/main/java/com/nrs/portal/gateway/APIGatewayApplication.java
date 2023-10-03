package com.nrs.portal.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@SpringBootApplication
@RequestScope
public class APIGatewayApplication {

	@RequestMapping("/hystrixfallback")
	public String hystrixfallback() {
		return "This is a fallback";
	}


	public static void main(String[] args) {
		SpringApplication.run(APIGatewayApplication.class, args);
	}
}
