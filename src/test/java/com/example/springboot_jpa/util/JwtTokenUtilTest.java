package com.example.springboot_jpa.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.springboot_jpa.BaseSpringBootTest;
import com.example.springboot_jpa.common.util.JwtTokenUtil;
import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenUtilTest extends BaseSpringBootTest {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Mock
	private User testUser;
	private String token;

	@BeforeEach
	void setUp() {
		// given: 테스트에 필요한 메소드 모킹
		when(testUser.getId()).thenReturn(1L);
		when(testUser.getNickname()).thenReturn(new Nickname("testUser"));

		// given: 토큰 생성
		token = jwtTokenUtil.createToken(testUser);
	}


	@Test
	@DisplayName("토큰이 정상적으로 생성된다.")
	void createToken() {
		// when: 토큰 생성
		String token = jwtTokenUtil.createToken(testUser);

		// then: 생성된 토큰이 null이 아니며, 길이가 0이 아님
		assertNotNull(token);
		assertFalse(token.isEmpty());
	}

	@Test
	@DisplayName("토큰에서 ID를 정상적으로 추출한다.")
	void getUserId() {
		// when: 토큰에서 ID 추출
		long expectedId = 1L;

		// then: 기대한 사용자 ID와 일치해야 함
		assertEquals(expectedId, jwtTokenUtil.getUserId(token));
	}

	@Test
	@DisplayName("토큰에서 닉네임을 정상적으로 추출한다.")
	void getNickname() {
		// when: 토큰에서 닉네임 추출
		String expected = "testUser";

		// then: 기대한 닉네임과 일치해야 함
		assertEquals(expected, jwtTokenUtil.getNickname(token));
	}

	@Test
	@DisplayName("토큰이 유효하면 true를 반환한다.")
	void validateToken() {
		// when: 토큰 검증
		boolean isValid = jwtTokenUtil.validateToken(token);

		// then: token이 유효하므로 true여야 함
		assertTrue(isValid);
	}

}
