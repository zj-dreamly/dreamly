package com.github.zj.dreamly.tool.exception;

import cn.hutool.core.util.StrUtil;
import com.github.zj.dreamly.tool.api.ResponseEntity;
import com.github.zj.dreamly.tool.api.SystemResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

/**
 * 全局的的异常拦截器
 *
 * @author 苍海之南
 */
@Slf4j
@Order
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity handleError(BusinessException e) {
		log.error("业务异常", e);
		return ResponseEntity.fail(e.getResultCode(), e.getMessage());
	}

	@ExceptionHandler(SecurityException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity handleError(SecurityException e) {
		log.error("认证异常", e);
		return ResponseEntity.fail(e.getResultCode(), e.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity handleError(Throwable e) {
		log.error("服务器异常", e);
		return ResponseEntity.fail(SystemResultCode.INTERNAL_SERVER_ERROR, (StrUtil.isEmpty(e.getMessage()) ? SystemResultCode.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage()));
	}
}
