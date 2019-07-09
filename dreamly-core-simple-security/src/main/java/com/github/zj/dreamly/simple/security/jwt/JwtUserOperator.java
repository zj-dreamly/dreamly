package com.github.zj.dreamly.simple.security.jwt;

import com.github.zj.dreamly.simple.security.constants.ConstantsSecurity;
import com.github.zj.dreamly.tool.exception.DreamlySecurityException;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 苍海之南
 */
@Slf4j
@AllArgsConstructor
public class JwtUserOperator {

	private static final String SECURITY_REQ_ATTR_USER = "com.github.zj.dreamly.security-user";

	private static final int SEVEN = 7;

	private final JwtOperator jwtOperator;

	/**
	 * 获取当前登录用户信息
	 *
	 * @return 用户信息
	 */
	public JwtUser getUser() {
		try {
			HttpServletRequest request = getRequest();
			String token = getTokenFromRequest(request);
			Boolean isValid = jwtOperator.validateToken(token);
			if (!isValid) {
				return null;
			}

			Object userInReq = request.getAttribute(SECURITY_REQ_ATTR_USER);
			if (userInReq != null) {
				return (JwtUser) userInReq;
			}
			JwtUser jwtUser = getUserFromToken(token);
			request.setAttribute(SECURITY_REQ_ATTR_USER, jwtUser);
			return jwtUser;
		} catch (Exception e) {
			throw new DreamlySecurityException("failed to get user information");
		}
	}

	/**
	 * 解析token，获得用户信息
	 *
	 * @param token token
	 * @return 用户信息
	 */
	@SuppressWarnings("unchecked")
	private JwtUser getUserFromToken(String token) {
		// 从token中获取user
		Claims claims = jwtOperator.getClaimsFromToken(token);
		Object roles = claims.get(JwtOperator.ROLES);
		Object permissions = claims.get(JwtOperator.PERMISSIONS);
		Object username = claims.get(JwtOperator.USERNAME);
		Object userId = claims.get(JwtOperator.USER_ID);

		return JwtUser.builder()
			.id(Long.valueOf(userId.toString()))
			.username((String) username)
			.roles((List<String>) roles)
			.permissions((List<String>) permissions)
			.build();
	}

	/**
	 * 从request中获取token
	 *
	 * @param request 请求
	 * @return token
	 */
	private String getTokenFromRequest(HttpServletRequest request) {
		String header = request.getHeader(ConstantsSecurity.AUTHORIZATION_HEADER);
		if (StringUtils.isEmpty(header)) {
			throw new DreamlySecurityException("No header named Authorization was found");
		}
		if (!header.startsWith(ConstantsSecurity.BEARER)) {
			throw new DreamlySecurityException("Token must begin with'Bearer '.");
		}
		if (header.length() <= SEVEN) {
			throw new DreamlySecurityException("Token illegal, length <= 7");
		}
		return header.substring(SEVEN);
	}

	/**
	 * 获取request
	 *
	 * @return request
	 */
	private static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if ((requestAttributes == null)) {
			throw new DreamlySecurityException("requestAttributes is null");
		}
		return ((ServletRequestAttributes) requestAttributes).getRequest();
	}
}
