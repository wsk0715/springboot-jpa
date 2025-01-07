package com.example.springboot_jpa.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class RequestTimeInterceptor implements HandlerInterceptor {

	private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) {
		startTime.set(System.currentTimeMillis());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response,
								Object handler,
								Exception ex) {
		long duration = System.currentTimeMillis() - startTime.get();
		log.info("[API Request Time] {} {} took {} ms", request.getMethod(), request.getRequestURI(), duration);
		startTime.remove();
	}

}
