package com.example.springboot_jpa.user.repository;

import com.example.springboot_jpa.user.domain.Nickname;
import com.example.springboot_jpa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByNickname(Nickname nickname);

}
