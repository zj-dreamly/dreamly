package com.github.zj.dreamly.tool.exception;

import com.github.zj.dreamly.tool.api.IResultCode;
import com.github.zj.dreamly.tool.api.SystemResultCode;
import lombok.Getter;

/**
 * @author: 苍海之南
 * @since: 0.0.1
 **/
public class DreamlySecurityException extends RuntimeException{
	private static final long serialVersionUID = 2359767895161832954L;

	@Getter
	private final IResultCode resultCode;

	public DreamlySecurityException(String message) {
		super(message);
		this.resultCode = SystemResultCode.UN_AUTHORIZED;
	}

	public DreamlySecurityException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public DreamlySecurityException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	public DreamlySecurityException(IResultCode resultCode, Object... args) {
		super(String.format(resultCode.getMessage(), args));
		this.resultCode = resultCode;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
