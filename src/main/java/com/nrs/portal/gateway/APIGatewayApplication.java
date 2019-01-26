package com.nrs.portal.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@SpringBootApplication
public class APIGatewayApplication {

	@RequestMapping("/hystrixfallback")
	public String hystrixfallback() {
		return "This is a fallback";
	}

	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		return http.httpBasic().and()
				.csrf().disable()
				.authorizeExchange()
				.pathMatchers("/**").authenticated()
				.anyExchange().permitAll()
				.and()
				.build();
	}




	@Bean
	public MapReactiveUserDetailsService reactiveUserDetailsService() {
		//UserDetails user = User.builder().passwordEncoder(encoder()).username("admin").password("$2a$10$5UM/t8/nehrvBiwsTxoBDeqBLSPz7gtzjMpH9KN/74Io8HpR70RX2").roles("USER").build();
		//return new MapReactiveUserDetailsService(user);

		User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder());
		UserDetails hubert = userBuilder.username("hubert").password("hubert").roles("USER").build();
		UserDetails admin = userBuilder.username("admin").password("adminPass").roles("USER", "ADMIN").build();
		return new MapReactiveUserDetailsService(hubert, admin);

	}

	//@Bean
	public Function<String, String> encoder() {
		BCryptPasswordEncoder encoder = bCryptPasswordEncoder();
		return encoder::encode;

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(APIGatewayApplication.class, args);
	}
}
