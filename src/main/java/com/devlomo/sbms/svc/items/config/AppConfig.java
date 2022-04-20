package com.devlomo.sbms.svc.items.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {
	
	@Bean("restClient")
	public RestTemplate registryRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id)
						.circuitBreakerConfig(CircuitBreakerConfig.custom()
								.slidingWindowSize(20)
								.failureRateThreshold(15)
								.waitDurationInOpenState(Duration.ofSeconds(25L))
								.slowCallRateThreshold(30)
								.slowCallDurationThreshold(Duration.ofSeconds(2L))
						.build())
						.timeLimiterConfig(TimeLimiterConfig.custom()
															.timeoutDuration(Duration.ofSeconds(5L))
											.build())
						.build();
		});
	}

}
