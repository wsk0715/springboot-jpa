package com.example.springboot_jpa.common.log.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TimeIntervalLogger {

	private final ThreadLocal<Long> startTime = new ThreadLocal<>();


	public void start() {
		startTime.set(System.currentTimeMillis());
	}

	public void log(String methodName, String requestUri) {
		long duration = System.currentTimeMillis() - startTime.get();
		log.info("[API Request Time] {} {} took {} ms", methodName, requestUri, duration);
		startTime.remove();
	}

}
