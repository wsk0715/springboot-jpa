package com.example.springboot_jpa.common.config;

import com.example.springboot_jpa.auth.interceptor.JwtInterceptor;
import com.example.springboot_jpa.auth.resolver.LoginUserArgumentResolver;
import com.example.springboot_jpa.common.log.interceptor.RequestInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final RequestInterceptor requestInterceptor;
	private final JwtInterceptor jwtInterceptor;
	private final LoginUserArgumentResolver loginUserArgumentResolver;


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor)
				.order(1);

		registry.addInterceptor(jwtInterceptor)
				.excludePathPatterns("/swagger-ui/**",
									 "/swagger-resources/**",
									 "/v3/api-docs/**",
									 "/webjars/**",
									 "/",
									 "/favicon.ico",
									 "/auth/**",
									 "/oauth/**")
				.order(2);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginUserArgumentResolver);
	}

}
