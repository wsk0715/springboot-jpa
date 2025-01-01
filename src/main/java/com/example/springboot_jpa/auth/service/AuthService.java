package com.example.springboot_jpa.auth.service;

import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import com.example.springboot_jpa.util.JwtCookieUtil;
import com.example.springboot_jpa.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;

	private final JwtCookieUtil jwtCookieUtil;

	private final JwtTokenUtil jwtTokenUtil;


	public void addJwtToCookie(HttpServletResponse response, String token) {
		jwtCookieUtil.addJwtToCookie(response, token);
	}

	public String extractJwtFromRequest(HttpServletRequest request) {
		return jwtCookieUtil.getJwtFromCookies(request);
	}

	public boolean validateToken(String token) {
		return jwtTokenUtil.validateToken(token);
	}

	public Long getUserId(String token) {
		return jwtTokenUtil.getUserId(token);
	}

	public User extractFromToken(String token) {
		return userService.findById(getUserId(token));
	}

}
