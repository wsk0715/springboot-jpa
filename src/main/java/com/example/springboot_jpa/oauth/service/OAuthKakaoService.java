package com.example.springboot_jpa.oauth.service;

import com.example.springboot_jpa.common.util.EncryptUtil;
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
public class OAuthKakaoService implements OAuthService {

	private final OAuthCommonService oAuthCommonService;

	private final OAuthRepository oAuthRepository;

	private final JwtTokenUtil jwtTokenUtil;

	private final EncryptUtil encryptUtil;


	@Override
	@Transactional
	public OAuthResult init(String code) {
		String accessToken = oAuthCommonService.getAccessToken(OAuthProvider.KAKAO, code);
		String kakaoId = String.valueOf(oAuthCommonService.getUserInfo(OAuthProvider.KAKAO, accessToken)
														  .get(OAuthProvider.KAKAO.getIdentifier()));

		// 최초 로그인 여부 확인
		String hashedCode = encryptUtil.hash(kakaoId);
		OAuth oauth = oAuthRepository.findByCode(hashedCode).orElse(null);
		boolean isInitialLogin = oauth == null;

		// 최초 로그인 시 = 소셜 로그인 정보 존재하지 않을 시
		if (isInitialLogin) {
			// 1. 임시 사용자 생성
			User user = oAuthCommonService.createTempUser(OAuthProvider.KAKAO);  // 임시 사용자

			// 2. 임시 사용자와 카카오 식별값 연결, 데이터베이스에 저장
			oauth = OAuth.create(user, OAuthProvider.KAKAO, hashedCode);
			oAuthRepository.save(oauth);
		}

		// JWT 발급
		User user = oauth.getUser();
		String token = jwtTokenUtil.createToken(user);

		return new OAuthResult(token);
	}

}
