package com.github.zj.dreamly.security.jwt.jwt;

import cn.hutool.core.util.IdUtil;
import com.github.zj.dreamly.security.jwt.constants.ConstantsSecurity;
import com.github.zj.dreamly.security.jwt.SecurityProperties;
import com.github.zj.dreamly.tool.exception.DreamlySecurityException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author itmuch.com
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("all")
public class JwtOperator {
	public static final String USER_ID = "id";
	public static final String USERNAME = "username";
	public static final String ROLES = "roles";
	public static final String PERMISSIONS = "permissions";
	private final SecurityProperties securityProperties;

	/**
	 * 从token中获取claim
	 *
	 * @param token token
	 * @return claim
	 */
	public Claims getClaimsFromToken(String token) {
		try {
			return Jwts.parser()
				.setSigningKey(this.securityProperties.getJwt().getSecret().getBytes())
				.parseClaimsJws(token)
				.getBody();

		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | io.jsonwebtoken.security.SecurityException | IllegalArgumentException e) {
			throw new DreamlySecurityException("Token invalided.");
		}
	}

	/**
	 * 获取token的过期时间
	 *
	 * @param token token
	 * @return 过期时间
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token).getExpiration();
	}

	/**
	 * 判断token是否过期
	 *
	 * @param token token
	 * @return 已过期返回true，未过期返回false
	 */
	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * 计算token的过期时间
	 *
	 * @return 过期时间
	 */
	private Date getExpirationTime() {
		return new Date(System.currentTimeMillis() + securityProperties.getJwt().getExpirationInSecond() * 1000);
	}

	/**
	 * 为指定用户生成token
	 *
	 * @param jwtUser 用户信息
	 * @return AuthInfo
	 */
	public AuthInfo generateToken(JwtUser jwtUser) {
		Map<String, Object> claims = new HashMap<>(3);
		claims.put(USER_ID, jwtUser.getId());
		claims.put(USERNAME, jwtUser.getUsername());
		claims.put(ROLES, jwtUser.getRoles());
		claims.put(PERMISSIONS, jwtUser.getPermissions());

		Date createdTime = new Date();
		Date expirationTime = this.getExpirationTime();

		byte[] keyBytes = this.securityProperties.getJwt().getSecret().getBytes();
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);

		final String token = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(createdTime)
			.setExpiration(expirationTime)
			.signWith(key)
			.compact();
		return AuthInfo.builder()
			.accessToken(token)
			.refreshToken(IdUtil.fastUUID())
			.id(jwtUser.getId())
			.userName(jwtUser.getUsername())
			.expiresIn(securityProperties.getJwt().getExpirationInSecond()).tokenType(ConstantsSecurity.BEARER).build();
	}

	/**
	 * 判断token是否非法
	 *
	 * @param token token
	 * @return 未过期返回true，否则返回false
	 */
	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
