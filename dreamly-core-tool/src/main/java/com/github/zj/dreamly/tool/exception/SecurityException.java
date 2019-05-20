package com.github.zj.dreamly.tool.exception;

import com.github.zj.dreamly.tool.api.IResultCode;
import com.github.zj.dreamly.tool.api.SystemResultCode;
import lombok.Getter;

/**
 * @author: 苍海之南
 * @since: 2019-05-20 12:19
 **/
public class SecurityException extends RuntimeException{
	private static final long serialVersionUID = 2359767895161832954L;

	@Getter
	private final IResultCode resultCode;

	public SecurityException(String message) {
		super(message);
		this.resultCode = SystemResultCode.UN_AUTHORIZED;
	}

	public SecurityException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public SecurityException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	public SecurityException(IResultCode resultCode, Object... args) {
		super(String.format(resultCode.getMessage(), args));
		this.resultCode = resultCode;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
