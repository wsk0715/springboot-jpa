package com.example.springboot_jpa.credential;

import com.example.springboot_jpa.exception.type.SpringbootJpaException;
import com.example.springboot_jpa.exception.type.UnauthorizedException;
import com.example.springboot_jpa.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

	@Value("${security.jwt.secretKey}")
	private String JWT_SECRET_KEY;

	@Value("${security.jwt.expirationMs}")
	private long JWT_EXPIRATION_MS;


	public String createToken(User user) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

		String token = Jwts.builder()
						   .setSubject(user.getNickname().getValue())
						   .claim("id", user.getId())
						   .claim("nickname", user.getNickname().getValue())
						   .setIssuedAt(now)
						   .setExpiration(expiryDate)
						   .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY.getBytes())
						   .compact();

		return token;
	}

	public long getUserId(String token) {
		Claims claims = getClaims(token);
		return claims.get("id", Long.class);
	}

	public String getNickname(String token) {
		Claims claims = getClaims(token);
		return claims.get("nickname", String.class);
	}

	private Claims getClaims(String token) {
		if (token == null) {
			throw new SpringbootJpaException("토큰이 존재하지 않습니다");
		}

		try {
			return Jwts.parser()
					   .setSigningKey(JWT_SECRET_KEY.getBytes())
					   .parseClaimsJws(token)
					   .getBody();
		} catch (JwtException | IllegalArgumentException e) {
			throw new UnauthorizedException("유효하지 않은 토큰입니다.");
		}
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(JWT_SECRET_KEY.getBytes())
				.parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

}

