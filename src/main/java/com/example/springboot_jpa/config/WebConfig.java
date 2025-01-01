package com.example.springboot_jpa.config;

import com.example.springboot_jpa.auth.interceptor.JwtInterceptor;
import com.example.springboot_jpa.auth.resolver.LoginUserArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final JwtInterceptor jwtInterceptor;

	private final LoginUserArgumentResolver loginUserArgumentResolver;


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
				.excludePathPatterns("/swagger-ui/**",
									 "/swagger-resources/**",
									 "/v3/api-docs/**",
									 "/webjars/**",
									 "/auth/**",
									 "/oauth/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginUserArgumentResolver);
	}

}
