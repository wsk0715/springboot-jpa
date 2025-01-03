package com.example.springboot_jpa.oauth.service;

import com.example.springboot_jpa.common.util.JwtTokenUtil;
import com.example.springboot_jpa.oauth.common.service.OAuthCommonService;
import com.example.springboot_jpa.oauth.constants.OAuthProvider;
import com.example.springboot_jpa.oauth.domain.OAuth;
import com.example.springboot_jpa.oauth.dto.OAuthResult;
import com.example.springboot_jpa.oauth.repository.OAuthRepository;
import com.example.springboot_jpa.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthGoogleService {

	private final OAuthCommonService oAuthCommonService;

	private final OAuthRepository oAuthRepository;

	private final JwtTokenUtil jwtTokenUtil;


	@Transactional
	public OAuthResult init(String code) {
		String accessToken = oAuthCommonService.getAccessToken(OAuthProvider.GOOGLE, code);
		String googleId = (String) oAuthCommonService.getUserInfo(OAuthProvider.GOOGLE, accessToken)
													 .get(OAuthProvider.GOOGLE.getIdentifier());

		// 최초 로그인 여부 확인
		OAuth oauth = oAuthRepository.findByCode(googleId).orElse(null);
		boolean isInitialLogin = oauth == null;

		// 최초 로그인 시 = 소셜 로그인 정보 존재하지 않을 시
		if (isInitialLogin) {
			// 1. 임시 사용자 생성
			User user = oAuthCommonService.createTempUser(OAuthProvider.GOOGLE);  // 임시 사용자

			// 2. 임시 사용자와 구글 식별값 연결, 데이터베이스에 저장
			oauth = OAuth.create(user, OAuthProvider.GOOGLE, googleId);
			oAuthRepository.save(oauth);
		}

		// JWT 발급
		User user = oauth.getUser();
		String token = jwtTokenUtil.createToken(user);

		return new OAuthResult(isInitialLogin, token);
	}

}
