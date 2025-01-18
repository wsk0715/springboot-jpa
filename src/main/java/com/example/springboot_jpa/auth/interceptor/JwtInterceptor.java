package com.example.springboot_jpa.auth.interceptor;


import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.common.exception.SpringbootJpaException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

	private final AuthService authService;


	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) {
		try {
			// 쿠키에서 토큰 String 가져오기
			String token = authService.getCredential(request);

			// 토큰이 없는 경우 처리
			if (token == null || token.isEmpty()) {
				log.warn("쿠키에 JWT 토큰이 존재하지 않습니다.");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return false;
			}

			// JWT 토큰 검증
			if (!authService.validateToken(token)) {
				log.warn("만료되었거나 유효하지 않은 JWT 토큰입니다.");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return false;
			}

			return true;
		} catch (SpringbootJpaException e) {
			log.warn("쿠키 처리 중 예외가 발생했습니다.");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response,
						   Object handler,
						   @Nullable ModelAndView modelAndView) {
	}

}
