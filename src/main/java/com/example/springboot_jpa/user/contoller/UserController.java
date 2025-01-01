package com.example.springboot_jpa.user.contoller;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.user.contoller.request.UserRequest;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserControllerDocs {

	private final UserService userService;


	@PatchMapping
	public ResponseEntity<BaseResponse> update(@RequestBody UserRequest userRequest,
											   @LoginUser User loginUser) {
		User user = userRequest.toUser();
		userService.update(user, loginUser);

		BaseResponse res = BaseResponse.ok()
									   .title("사용자 정보 수정 성공")
									   .description("사용자 정보를 수정했습니다.")
									   .build();

		return ResponseEntity.ok(res);
	}

	@DeleteMapping
	public ResponseEntity<BaseResponse> delete(@LoginUser User loginUser) {
		userService.delete(loginUser);

		BaseResponse res = BaseResponse.ok()
									   .title("사용자 제거 성공")
									   .description("사용자 정보를 제거했습니다. (로그아웃 API 호출 필요)")
									   .build();

		return ResponseEntity.ok(res);
	}

}
