package com.example.springboot_jpa;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseSpringBootTest {
	// 상속받는 모든 테스트 클래스에 "test" 프로파일 적용
}
