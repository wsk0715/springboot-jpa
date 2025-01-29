package com.example.springboot_jpa.validation;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

/**
 * 유효성 검사를 수행하는 함수형 인터페이스<br>
 * 람다식을 통해 구현부를 전달받아, 하나의 클래스로 다양한 도메인을 검증한다.
 */
@FunctionalInterface
public interface Validator {

	void validate() throws Exception;  // 람다식을 통해 구현부를 전달받는다. 파라미터가 존재하지 않고, 반환값이 void

	static ValidationResult validate(Validator validator) {
		try {
			validator.validate();  // 인자로 받은 람다식이 validate()의 구현부로 사용된다.
			return new ValidationResult(true);
		} catch (Exception e) {
			return new ValidationResult(false);
		}
	}

	@RequiredArgsConstructor
	class ValidationResult {

		private final boolean valid;

		public <X extends Throwable> void orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
			if (!valid) {
				throw exceptionSupplier.get();  // 인자로 받은 람다식이 get()의 구현부로 사용된다.
			}
		}
		
	}

}
