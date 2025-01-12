package com.example.springboot_jpa.common.log.interceptor;

import com.example.springboot_jpa.common.log.logger.QueryExecutionLogger;
import com.example.springboot_jpa.common.log.logger.TimeIntervalLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {

	private final TimeIntervalLogger timeIntervalLogger;
	private final QueryExecutionLogger queryExecutionLogger;


	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) {
		timeIntervalLogger.start();
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response,
								Object handler,
								Exception ex) {
		logRequestTime(request);
		logQueryExecutionTime();
	}

	private void logRequestTime(HttpServletRequest request) {
		String methodName = request.getMethod();
		String requestUri = request.getRequestURI();
		timeIntervalLogger.log(methodName, requestUri);
	}

	private void logQueryExecutionTime() {
		queryExecutionLogger.log();
	}

}
