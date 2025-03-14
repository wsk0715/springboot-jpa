package com.example.springboot_jpa.user.service;

import com.example.springboot_jpa.exception.type.status4xx.ConflictException;
import com.example.springboot_jpa.exception.type.status4xx.NotFoundException;
import com.example.springboot_jpa.user.domain.User;
import com.example.springboot_jpa.user.domain.vo.Nickname;
import com.example.springboot_jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;


	public void save(User user) {
		userRepository.save(user);
	}

	public User findById(Long userId) {
		return userRepository.findById(userId)
							 .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));
	}

	public User findUserByNickname(String nickname) {
		return userRepository.findByNickname(Nickname.of(nickname))
							 .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));
	}

	public boolean existsByNickname(String nickname) {
		return userRepository.existsByNickname(Nickname.of(nickname));
	}

	@Transactional
	public void update(User user, User loginUser) {
		// 사용자 확인
		Long loginUserId = loginUser.getId();
		User dbUser = findById(loginUserId);

		// 닉네임 변경
		Nickname nickname = user.getNickname();
		if (userRepository.existsByNickname(nickname)) {
			throw new ConflictException("이미 존재하는 닉네임입니다.");
		}

		dbUser.updateNickname(nickname);
	}

	@Transactional
	public void delete(User user) {
		// 사용자 확인
		Long loginUserId = user.getId();
		findById(loginUserId);

		// 사용자 제거
		userRepository.deleteById(loginUserId);
	}

}
