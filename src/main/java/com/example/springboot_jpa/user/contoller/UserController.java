package com.example.springboot_jpa.user.contoller;

import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.service.UserService;
import com.example.springboot_jpa.util.JwtCookieUtil;
import com.example.springboot_jpa.util.JwtTokenUtil;
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

	private final JwtTokenUtil jwtTokenUtil;


	@PatchMapping
	public ResponseEntity<BaseResponse> modify(@RequestBody Nickname nickname,
											   HttpServletRequest request) {
		String token = jwtCookieUtil.getJwtFromCookies(request);

		Long userId = jwtTokenUtil.getUserId(token);
		String newNickname = nickname.getNickname();
		userService.updateNickname(userId, newNickname);

		BaseResponse res = BaseResponse.ok()
									   .title("닉네임 변경 성공")
									   .description("닉네임을 변경했습니다.")
									   .build();

		return ResponseEntity.ok(res);
	}

}
