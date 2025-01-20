package com.example.springboot_jpa.oauth.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springboot_jpa.BaseSpringBootTest;
import com.example.springboot_jpa.auth.service.AuthService;
import com.example.springboot_jpa.common.credential.dto.Credential;
import com.example.springboot_jpa.oauth.properties.OAuthGoogleProperties;
import com.example.springboot_jpa.oauth.properties.OAuthKakaoProperties;
import com.example.springboot_jpa.oauth.service.OAuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties(OAuthGoogleProperties.class)
class OAuthControllerTest extends BaseSpringBootTest {

	private final String providerGoogle = "google";
	private final String providerKakao = "kakao";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OAuthGoogleProperties oAuthGoogleProperties;

	@MockBean
	private OAuthKakaoProperties oAuthKakaoProperties;

	@MockBean
	private OAuthService oAuthService;

	@MockBean
	private AuthService authService;


	@Test
	@DisplayName("구글 로그인 요청에 대해 인증 URL이 담긴 응답을 반환한다.")
	void authenticationGoogle() throws Exception {
		// given
		String expectedUrl = oAuthGoogleProperties.getUrl();

		// when & then
		mockMvc.perform(get("/oauth/google"))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.*", hasItem(expectedUrl)));
	}

	@Test
	@DisplayName("구글 code를 입력하면 JWT 토큰을 반환한다.")
	void callbackGoogle() throws Exception {
		// given
		String dummyCode = "dummyCode";
		String token = "dummyToken";
		Credential dummyResult = new Credential(token);

		// init() 메서드에 입력값과 결과를 모킹한다.
		when(oAuthService.init(providerGoogle, dummyCode)).thenReturn(dummyResult);

		// authService 및 jwtCookieUtil에 대한 동작을 모킹한다. (실제 쿠키 추가 로직을 실행하지 않기 위함)
		when(authService.setCredential(any(), eq(dummyResult.jwt()))).thenReturn(new Credential(token));

		// when & then
		mockMvc.perform(get("/oauth/google/callback")
								.param("code", dummyCode))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.*", hasItem(dummyResult.jwt())));
	}

	@Test
	@DisplayName("카카오 로그인 요청에 대해 인증 URL이 담긴 응답을 반환한다.")
	void authenticationKakao() throws Exception {
		// given
		String expectedUrl = oAuthKakaoProperties.getUrl();

		// when & then
		mockMvc.perform(get("/oauth/kakao"))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.*", hasItem(expectedUrl)));
	}

	@Test
	@DisplayName("카카오 code를 입력하면 JWT 토큰을 반환한다.")
	void callbackKakao() throws Exception {
		// given
		String dummyCode = "dummyCode";
		String token = "dummyToken";
		Credential dummyResult = new Credential(token);

		// init() 메서드에 입력값과 결과를 모킹한다.
		when(oAuthService.init(providerKakao, dummyCode)).thenReturn(dummyResult);

		// authService 및 jwtCookieUtil에 대한 동작을 모킹한다. (실제 쿠키 추가 로직을 실행하지 않기 위함)
		when(authService.setCredential(any(), eq(dummyResult.jwt()))).thenReturn(new Credential(token));

		// when & then
		mockMvc.perform(get("/oauth/kakao/callback")
								.param("code", dummyCode))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.*", hasItem(dummyResult.jwt())));
	}

}
