package com.example.springboot_jpa.user.controller;

import com.example.springboot_jpa.auth.annotation.LoginUser;
import com.example.springboot_jpa.user.controller.request.UserRequest;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import jakarta.validation.Valid;
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
	public ResponseEntity<Void> update(@Valid @RequestBody UserRequest userRequest,
									   @LoginUser User loginUser) {
		User user = userRequest.toUser();
		userService.update(user, loginUser);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> delete(@LoginUser User loginUser) {
		userService.delete(loginUser);

		return ResponseEntity.ok().build();
	}

}
