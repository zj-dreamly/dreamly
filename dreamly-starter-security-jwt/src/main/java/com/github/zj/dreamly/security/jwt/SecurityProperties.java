package com.github.zj.dreamly.security.jwt;

import com.github.zj.dreamly.security.jwt.spec.Spec;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置属性
 *
 * @author 苍海之南
 */
@ConfigurationProperties(prefix = "dreamly.security")
@Getter
@Setter
@SuppressWarnings("all")
public class SecurityProperties {
	private Jwt jwt = new Jwt();

	/**
	 * permission list
	 */
	private List<Spec> specList = new ArrayList<>();

	@Getter
	@Setter
	public static class Jwt {
		/**
		 * secret
		 */
		private String secret = "I-Love-You-My-Lovely-Baby-I-Love-You-My-Lovely-Baby-I-Love-You-My-Lovely-Baby";

		/**
		 * Token validity time (seconds), default is 2 weeks
		 */
		private Long expirationInSecond = 1209600L;

		/**
		 * Signing algorithm, default sha512
		 */
		private SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;
	}
}

