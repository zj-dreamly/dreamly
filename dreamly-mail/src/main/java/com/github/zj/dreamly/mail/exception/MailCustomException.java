package com.github.zj.dreamly.mail.exception;

import lombok.NoArgsConstructor;

/**
 * <h2>MailPlusException</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-04 17:01
 **/
@NoArgsConstructor
public class MailCustomException extends Exception {
	private static final long serialVersionUID = -8014572218613182580L;

	public MailCustomException(String message) {
		super(message);
	}
}
