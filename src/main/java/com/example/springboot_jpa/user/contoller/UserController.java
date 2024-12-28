package com.example.springboot_jpa.user.contoller;

import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.user.contoller.request.UserRequest;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import com.example.springboot_jpa.util.JwtCookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserControllerDocs {

	private final UserService userService;
	
	private final JwtCookieUtil jwtCookieUtil;


	@PatchMapping
	public ResponseEntity<BaseResponse> update(@RequestBody UserRequest userRequest,
											   HttpServletRequest request) {
		String token = jwtCookieUtil.getJwtFromCookies(request);
		User user = userRequest.toUser();
		userService.update(token, user);

		BaseResponse res = BaseResponse.ok()
									   .title("사용자 정보 수정 성공")
									   .description("사용자 정보를 수정했습니다.")
									   .build();

		return ResponseEntity.ok(res);
	}

}
