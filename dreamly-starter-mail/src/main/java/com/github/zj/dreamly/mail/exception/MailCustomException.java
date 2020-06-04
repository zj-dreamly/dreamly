package com.github.zj.dreamly.mail.exception;

import lombok.NoArgsConstructor;

/**
 * <h2>MailPlusException</h2>
 *
 * @author: zeemoo
 * @since: 0.0.1
 **/
@NoArgsConstructor
public class MailCustomException extends Exception {
	private static final long serialVersionUID = -8014572218613182580L;

	public MailCustomException(String message) {
		super(message);
	}
}
