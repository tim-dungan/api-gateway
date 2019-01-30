package com.nrs.portal.gateway;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APIGatewayApplicationTests {

	@LocalServerPort
	int port;
	private WebTestClient client;

	@Before
	public void setup() {
		client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void pathRouteWorks() {
		client.get().uri("/account")
				.exchange()
				.expectStatus().is4xxClientError()
				.expectBody(Map.class)
				.consumeWith(result -> {
					System.out.println(result);
				});
	}

	@Test
	public void rateLimiterWorks() {
		WebTestClient authClient = client.mutate()
				.filter(basicAuthentication("admin", "adminPass"))
				.build();

		authClient.get().uri("/account")
				.exchange();



	}



}
