package com.example.springboot_jpa.auth.service;

import com.example.springboot_jpa.auth.controller.request.LoginRequest;
import com.example.springboot_jpa.auth.domain.Auth;
import com.example.springboot_jpa.auth.domain.vo.AuthLoginId;
import com.example.springboot_jpa.auth.repository.AuthRepository;
import com.example.springboot_jpa.common.util.NicknameUtil;
import com.example.springboot_jpa.credential.service.CredentialService;
import com.example.springboot_jpa.exception.type.status4xx.BadRequestException;
import com.example.springboot_jpa.exception.type.status4xx.UnauthorizedException;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.domain.vo.Nickname;
import com.example.springboot_jpa.user.service.UserService;
import com.example.springboot_jpa.validation.Validator;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepository authRepository;

	private final UserService userService;

	private final CredentialService credentialService;

	public Auth signup(Auth auth) {
		// 1. 임시 유저 생성 및 연결
		User user = createTempUser();
		userService.save(user);

		// 2. 유저 인증 정보 저장
		Validator.validate(() -> auth.updateUser(user))
				 .orElseThrow(() -> new BadRequestException("회원 정보가 존재하지 않습니다."));

		return authRepository.save(auth);
	}

	private User createTempUser() {
		// 1. 랜덤 닉네임 생성, 중복 확인
		String tmpNickname = NicknameUtil.generateRandom("user");
		while (userService.existsByNickname(Nickname.of(tmpNickname))) {
			tmpNickname = NicknameUtil.generateRandom("user");
		}

		// 2. 임시 유저 생성
		User user = User.create(Nickname.of(tmpNickname));
		userService.save(user);

		return user;
	}

	public void login(LoginRequest loginRequest, HttpServletResponse response) {
		// 1. DB에서 로그인 정보 받아오기
		AuthLoginId loginId = AuthLoginId.of(loginRequest.loginId());
		Auth dbAuth = Optional.ofNullable(authRepository.findByLoginId(loginId))
							  .orElseThrow(() -> new UnauthorizedException("입력한 정보가 일치하지 않습니다."));

		// 2. 해당 로그인 정보의 비밀번호와 사용자가 입력한 비밀번호 확인
		String password = loginRequest.password();
		if (!dbAuth.comparePasswordWith(password)) {
			throw new UnauthorizedException("입력한 정보가 일치하지 않습니다.");
		}

		// 3. 인증 정보 설정을 위해 User 엔티티 받아오기
		Long userId = dbAuth.getUser().getId();
		User dbUser = userService.findById(userId);

		// 4. 인증 정보 설정
		credentialService.setCredential(response, dbUser);
	}

}
