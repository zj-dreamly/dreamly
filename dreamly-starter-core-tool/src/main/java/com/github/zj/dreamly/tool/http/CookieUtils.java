package com.github.zj.dreamly.tool.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>CookieUtils</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.1
 **/
public class CookieUtils {

	/**
	 * 设置
	 *
	 * @param response response
	 * @param name     Cookie name
	 * @param value    Cookie value
	 * @param maxAge   有效期
	 */
	public static void set(HttpServletResponse response,
						   String name,
						   String value,
						   int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/**
	 * 获取cookie
	 *
	 * @param request request
	 * @param name    Cookie name
	 * @return 结果
	 */
	public static Cookie get(HttpServletRequest request,
							 String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		return cookieMap.getOrDefault(name, null);
	}

	/**
	 * 将cookie封装成Map
	 *
	 * @param request request
	 * @return 结果
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<>(8);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 获取Cookie的值
	 *
	 * @param request  request
	 * @param cookName cookName
	 * @return 结果
	 */
	public static String getCookieValue(HttpServletRequest request, String cookName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookName)) {
				return cookie.getValue();
			}
		}

		return null;
	}
}
