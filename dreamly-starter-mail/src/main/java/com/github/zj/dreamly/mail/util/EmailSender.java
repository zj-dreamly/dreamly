package com.github.zj.dreamly.mail.util;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailException;
import cn.hutool.extra.mail.MailUtil;
import com.github.zj.dreamly.tool.exception.BusinessException;

import java.io.File;

/**
 * <h2>EmailSender</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.1
 **/
public class EmailSender {

	public void send(MailAccount mailAccount, String email,
					 String subject, String html) {

		try {
			MailUtil.send(mailAccount, email, subject, html, true);
		} catch (MailException ex) {
			throw new BusinessException("邮件服务器异常");
		}

	}

	public void sendWithReply(MailAccount mailAccount, String email, String subject, String html,
							  String... reply) {

		final Mail mail = Mail.create(mailAccount).setUseGlobalSession(false);
		mail.setReply(reply);
		mail.setTos(StrUtil.splitToArray(email, CharUtil.COMMA));
		mail.setTitle(subject);
		mail.setContent(html);
		mail.setHtml(true);
		mail.send();
	}

	public void sendWithAttachment(MailAccount mailAccount, String email, String subject,
								   String text, File file) {
		try {
			MailUtil.send(mailAccount, email, subject, text, false, file);
		} catch (MailException ex) {
			throw new BusinessException("邮件服务器异常");
		}
	}
}
