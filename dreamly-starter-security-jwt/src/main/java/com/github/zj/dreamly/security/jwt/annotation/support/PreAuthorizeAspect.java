package com.github.zj.dreamly.security.jwt.annotation.support;

import com.github.zj.dreamly.security.jwt.annotation.PreAuthorize;
import com.github.zj.dreamly.security.jwt.el.PreAuthorizeExpressionRoot;
import com.github.zj.dreamly.security.jwt.util.SpringElCheckUtil;
import com.github.zj.dreamly.tool.exception.DreamlySecurityException;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

import static com.github.zj.dreamly.tool.constant.SystemConstant.DEFAULT_UNAUTHORIZED_MESSAGE;

/**
 * 处理PreAuthorize注解的切面
 *
 * @author itmuch.com
 */
@Aspect
@AllArgsConstructor
public class PreAuthorizeAspect {
	private final PreAuthorizeExpressionRoot preAuthorizeExpressionRoot;

	@Around("@annotation(com.github.zj.dreamly.security.jwt.annotation.PreAuthorize) ")
	public Object preAuth(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		if (method.isAnnotationPresent(PreAuthorize.class)) {
			PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);

			String expression = preAuthorize.value();
			boolean check = SpringElCheckUtil.check(
				new StandardEvaluationContext(preAuthorizeExpressionRoot),
				expression
			);
			if (!check) {
				throw new DreamlySecurityException(DEFAULT_UNAUTHORIZED_MESSAGE);
			}
		}
		return point.proceed();
	}
}

