package com.example.springboot_jpa.user.contoller;

import com.example.springboot_jpa.response.BaseResponse;
import com.example.springboot_jpa.user.contoller.request.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "User 도메인 관련 API")
public interface UserControllerDocs {

	@Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정한다.")
	@ApiResponses(value = {
			@ApiResponse(description = "수정 성공", responseCode = "200"),
	})
	ResponseEntity<BaseResponse> update(@RequestBody UserRequest userRequest, HttpServletRequest request);

}
