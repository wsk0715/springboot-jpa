package com.example.springboot_jpa.auth.resolver;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final AuthService authService;


	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// @LoginUser 어노테이션이 붙어 있고, 파라미터 타입이 User인 경우에 적용
		return parameter.hasParameterAnnotation(LoginUser.class)
			   && parameter.getParameterType().equals(User.class);
	}

	@Override
	public User resolveArgument(@NonNull MethodParameter parameter,
								ModelAndViewContainer mavContainer,
								NativeWebRequest webRequest,
								WebDataBinderFactory binderFactory) {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

		// 요청이 보유한 토큰을 이용해 실제 존재하는 사용자인지 조회
		String token = authService.getCredential(request);
		User user = authService.extractFromToken(token);
		log.info("사용자 로그인: {}", user.toString());

		return user;
	}

}
