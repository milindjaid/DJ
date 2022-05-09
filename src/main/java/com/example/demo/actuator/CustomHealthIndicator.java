package com.example.demo.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

	public final static String MESSEGE_KEY = "Spring_Hibernate_Integration_service";

	@Override
	public Health health() {

		if(!isHealthUp()) {
			return Health.down().withDetail(MESSEGE_KEY, Status.DOWN).build();
		}
		return Health.up().withDetail(MESSEGE_KEY, Status.UP).build();
	}

	public boolean isHealthUp() {
		return false;
	}

}
