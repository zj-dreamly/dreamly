package com.github.zj.dreamly.tool.exception;

import com.github.zj.dreamly.tool.api.IResultCode;
import com.github.zj.dreamly.tool.api.SystemResultCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务异常
 *
 * @author Chill
 */
@Slf4j
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 2359767895161832954L;

	@Getter
	private final IResultCode resultCode;

	public BusinessException(String message) {
		super(message);
		this.resultCode = SystemResultCode.FAILURE;
	}

	public BusinessException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public BusinessException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	public BusinessException(IResultCode resultCode, Object... args) {
		super(String.format(resultCode.getMessage(), args));
		this.resultCode = resultCode;
	}

	/**
	 * 提高性能
	 *
	 * @return Throwable
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}
}
