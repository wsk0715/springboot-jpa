package com.example.springboot_jpa.user.contoller;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.user.contoller.request.UserRequest;
import com.example.springboot_jpa.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "User 도메인 관련 API")
public interface UserControllerDocs {

	@Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "수정 성공", responseCode = "200"),
	})
	ResponseEntity<Void> update(@RequestBody UserRequest userRequest,
								@Parameter(hidden = true) @LoginUser User user);

	@Operation(summary = "사용자 제거", description = "사용자를 제거한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "제거 성공", responseCode = "200"),
	})
	ResponseEntity<Void> delete(@Parameter(hidden = true) @LoginUser User user);

}
