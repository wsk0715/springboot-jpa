package com.example.springboot_jpa.oauth.google.service;

import static com.example.springboot_jpa.util.NicknameGenerator.generateRandomNickname;

import com.example.springboot_jpa.oauth.google.domain.OAuthGoogle;
import com.example.springboot_jpa.oauth.google.repository.OAuthGoogleRepository;
import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.service.UserService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
public class OAuthGoogleService {

	@Value("${security.oauth.google.clientId}")
	private String GOOGLE_CLIENT_ID;

	@Value("${security.oauth.google.clientSecret}")
	private String GOOGLE_CLIENT_SECRET;

	@Value("${security.oauth.google.redirectUri}")
	private String GOOGLE_REDIRECT_URI;

	@Value("${security.oauth.google.tokenUrl}")
	private String GOOGLE_TOKEN_URL;

	@Value("${security.oauth.google.userInfoUrl}")
	private String GOOGLE_USERINFO_URL;

	private final UserService userService;

	private final OAuthGoogleRepository oAuthGoogleRepository;


	@Transactional
	public boolean init(String code) {
		String accessToken = getAccessToken(code);
		String googleSub = (String) getUserInfo(accessToken).get("sub");  // sub - 구글 고유 식별자

		// 소셜 로그인 정보 존재하지 않을 시
		if (!oAuthGoogleRepository.existsByCode(googleSub)) {
			// 1. 임시 사용자 생성
			User user = createTempUser();
			Long userId = user.getId();  // 임시 사용자 id

			// 2. 임시 사용자와 구글 sub 식별값 연결
			OAuthGoogle oauth = OAuthGoogle.create(userId, googleSub);
			oAuthGoogleRepository.save(oauth);

			return false;
		}

		return true;
	}

	private String getAccessToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// 액세스 토큰 요청 파라미터 설정
		String body = "code=" + code +
					  "&client_id=" + GOOGLE_CLIENT_ID +
					  "&client_secret=" + GOOGLE_CLIENT_SECRET +
					  "&redirect_uri=" + GOOGLE_REDIRECT_URI +
					  "&grant_type=authorization_code";

		HttpEntity<String> entity = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(GOOGLE_TOKEN_URL, HttpMethod.POST, entity, Map.class);

		return (String) response.getBody().get("access_token");
	}

	private Map<String, Object> getUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		System.out.println("액세스 토큰 확인 중");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(GOOGLE_USERINFO_URL, HttpMethod.GET, entity, Map.class);
		System.out.println("액세스 토큰 확인 완료");

		return response.getBody();
	}

	private User createTempUser() {
		// 1. 랜덤 닉네임 생성, 중복 확인
		String tmpNickname = generateRandomNickname("google");
		while (userService.existsByNickname(new Nickname(tmpNickname))) {
			tmpNickname = generateRandomNickname("google");
		}

		// 2. 임시 유저 생성
		User user = User.create(tmpNickname);
		userService.save(user);

		return user;
	}

}
