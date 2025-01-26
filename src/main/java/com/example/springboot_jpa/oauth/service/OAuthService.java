package com.example.springboot_jpa.oauth.service;

import com.example.springboot_jpa.common.encryption.HashEncryptUtil;
import com.example.springboot_jpa.common.util.NicknameUtil;
import com.example.springboot_jpa.credential.JwtTokenUtil;
import com.example.springboot_jpa.credential.service.CredentialService;
import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import com.example.springboot_jpa.oauth.constants.OAuthProvider;
import com.example.springboot_jpa.oauth.domain.OAuth;
import com.example.springboot_jpa.oauth.properties.OAuthGoogleProperties;
import com.example.springboot_jpa.oauth.properties.OAuthKakaoProperties;
import com.example.springboot_jpa.oauth.repository.OAuthRepository;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.domain.vo.Nickname;
import com.example.springboot_jpa.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties({OAuthGoogleProperties.class, OAuthKakaoProperties.class})
public class OAuthService {

	private final OAuthGoogleProperties googleProperties;

	private final OAuthKakaoProperties kakaoProperties;

	private final JwtTokenUtil jwtTokenUtil;

	private final UserService userService;

	private final CredentialService credentialService;

	private final OAuthRepository oAuthRepository;


	public String getUrl(String provider) {
		if (provider.equals(OAuthProvider.GOOGLE.getCode())) {
			return googleProperties.getUrl();
		}

		if (provider.equals(OAuthProvider.KAKAO.getCode())) {
			return kakaoProperties.getUrl();
		}

		throw new SpringbootJpaException("OAuth 제공자가 올바르지 않습니다.");
	}

	@Transactional
	public void init(String provider, String code, HttpServletResponse response) {
		OAuthProvider oAuthProvider = OAuthProvider.findByName(provider);
		if (oAuthProvider == null) {
			throw new SpringbootJpaException("OAuth 제공자가 올바르지 않습니다.");
		}

		String accessToken = getAccessToken(oAuthProvider, code);
		String oAuthUserId = String.valueOf(getUserInfo(oAuthProvider, accessToken)
													.get(oAuthProvider.getIdentifier()));

		// 최초 로그인 여부 확인
		String hashedCode = HashEncryptUtil.hash(oAuthUserId);
		OAuth oauth = oAuthRepository.findByCode(hashedCode).orElse(null);
		boolean isInitialLogin = oauth == null;

		// 최초 로그인 시 = 소셜 로그인 정보 존재하지 않을 시
		if (isInitialLogin) {
			// 1. 임시 사용자 생성
			User user = createTempUser(oAuthProvider);  // 임시 사용자

			// 2. 임시 사용자와 구글 식별값 연결, 데이터베이스에 저장
			oauth = OAuth.create(user, oAuthProvider, hashedCode);
			oAuthRepository.save(oauth);
		}

		// 요청에 인증 정보 설정
		User user = oauth.getUser();
		credentialService.setCredential(response, user);
	}


	private User createTempUser(OAuthProvider provider) {
		// 1. 랜덤 닉네임 생성, 중복 확인
		String tmpNickname = NicknameUtil.generateRandom(provider);
		while (userService.existsByNickname(Nickname.of(tmpNickname))) {
			tmpNickname = NicknameUtil.generateRandom(provider);
		}

		// 2. 임시 유저 생성
		User user = User.create(Nickname.of(tmpNickname));
		userService.save(user);

		return user;
	}

	private String getAccessToken(OAuthProvider provider, String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 액세스 토큰 요청 파라미터 설정
		String body, tokenUrl;
		switch (provider) {
			case GOOGLE:
				body = googleProperties.getBody(code);
				tokenUrl = googleProperties.tokenUrl();
				break;
			case KAKAO:
				body = kakaoProperties.getBody(code);
				tokenUrl = kakaoProperties.tokenUrl();
				break;
			default:
				throw new SpringbootJpaException("올바르지 않은 OAuth 서비스 제공자입니다.");
		}

		HttpEntity<String> entity = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);

		return (String) response.getBody().get("access_token");
	}

	private Map<String, Object> getUserInfo(OAuthProvider provider, String accessToken) {
		// 사용자 정보를 요청할 url 설정
		String userInfoUrl;
		switch (provider) {
			case GOOGLE:
				userInfoUrl = googleProperties.userInfoUrl();
				break;
			case KAKAO:
				userInfoUrl = kakaoProperties.userInfoUrl();
				break;
			default:
				throw new SpringbootJpaException("올바르지 않은 OAuth 서비스 제공자입니다.");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		System.out.println("액세스 토큰 확인 중");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
		System.out.println("액세스 토큰 확인 완료");

		return response.getBody();
	}

}
