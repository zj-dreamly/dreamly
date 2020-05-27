package com.github.zj.dreamly.security.jwt.interceptor;

import cn.hutool.core.util.StrUtil;
import com.github.zj.dreamly.security.jwt.annotation.PreAuthorize;
import com.github.zj.dreamly.security.jwt.el.PreAuthorizeExpressionRoot;
import com.github.zj.dreamly.security.jwt.spec.Spec;
import com.github.zj.dreamly.security.jwt.util.RestfulMatchUtil;
import com.github.zj.dreamly.security.jwt.util.SpringElCheckUtil;
import com.github.zj.dreamly.tool.exception.DreamlySecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static com.github.zj.dreamly.tool.constant.SystemConstant.DEFAULT_UNAUTHORIZED_MESSAGE;

/**
 * 授权控制拦截器
 *
 * @author 苍海之南
 */
@RequiredArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private final List<Spec> specList;
	private final PreAuthorizeExpressionRoot preAuthorizeExpressionRoot;
	private final static String ANON = "anon()";
	private final static String ERROR_PREFIX = "/error";
	private final static String FAVICON_PREFIX = "/favicon.ico";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			PreAuthorize preAuthorize = hm.getMethodAnnotation(PreAuthorize.class);
			final Optional<Boolean> optional = Optional.ofNullable(preAuthorize)
				.map(PreAuthorize::value)
				.map(value -> value.equals(ANON));
			if (optional.isPresent() && optional.get().equals(true)) {
				return true;
			}
		}

		final String uri = request.getRequestURI();
		if (StrUtil.startWithIgnoreCase(uri, ERROR_PREFIX) ||
			StrUtil.startWithIgnoreCase(uri, FAVICON_PREFIX)) {
			return true;
		}

		// 当前请求的路径和定义的规则能够匹配
		Boolean checkResult = specList.stream()
			.filter(spec -> RestfulMatchUtil.match(request, spec.getHttpMethod(), spec.getPath()))
			.findFirst()
			.map(spec -> {
				String expression = spec.getExpression();
				return SpringElCheckUtil.check(
					new StandardEvaluationContext(preAuthorizeExpressionRoot),
					expression
				);

			})
			.orElse(true);
		if (!checkResult) {
			throw new DreamlySecurityException(DEFAULT_UNAUTHORIZED_MESSAGE);
		}
		return true;
	}
}

