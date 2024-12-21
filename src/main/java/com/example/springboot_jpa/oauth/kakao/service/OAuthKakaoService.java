package com.example.springboot_jpa.oauth.kakao.service;

import com.example.springboot_jpa.oauth.common.service.OAuthCommonService;
import com.example.springboot_jpa.oauth.constants.OAuthProvider;
import com.example.springboot_jpa.oauth.dto.OAuthResult;
import com.example.springboot_jpa.oauth.kakao.domain.OAuthKakao;
import com.example.springboot_jpa.oauth.kakao.repository.OAuthKakaoRepository;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthKakaoService {

	private final OAuthCommonService oAuthCommonService;

	private final OAuthKakaoRepository oAuthKakaoRepository;

	private final JwtTokenProvider jwtTokenProvider;


	@Transactional
	public OAuthResult init(String code) {
		String accessToken = oAuthCommonService.getAccessToken(OAuthProvider.KAKAO, code);
		String kakaoId = String.valueOf(oAuthCommonService.getUserInfo(OAuthProvider.KAKAO, accessToken)
														  .get(OAuthProvider.KAKAO.getIdentifier()));

		// 최초 로그인 여부 확인
		OAuthKakao oauth = oAuthKakaoRepository.findByCode(kakaoId).orElse(null);
		boolean isInitialLogin = oauth == null;

		// 최초 로그인 시 = 소셜 로그인 정보 존재하지 않을 시
		if (isInitialLogin) {
			// 1. 임시 사용자 생성
			User user = oAuthCommonService.createTempUser(OAuthProvider.KAKAO);  // 임시 사용자

			// 2. 임시 사용자와 카카오 식별값 연결, 데이터베이스에 저장
			oauth = OAuthKakao.create(user, kakaoId);
			oAuthKakaoRepository.save(oauth);
		}

		// JWT 발급
		User user = oauth.getUser();
		String token = jwtTokenProvider.createToken(user);

		return new OAuthResult(isInitialLogin, token);
	}

}
