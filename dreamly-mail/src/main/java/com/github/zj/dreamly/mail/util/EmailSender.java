package com.github.zj.dreamly.mail.util;

import com.github.zj.dreamly.mail.exception.MailCustomException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

/**
 * <h2>EmailSender</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-04 17:03
 **/
public class EmailSender {
	private static final String UTF8 = "UTF-8";

	/**
	 * 发送邮件
	 */
	public static void sendEmailWithAttachment(String username, String password,
											   String title, String receiver, String content,
											   String mailSploitName,
											   String mailSploitEmail, File file, String host, Integer port,
											   String socksProxyHost, Integer socksProxyPort
	) throws MailCustomException {
		try {
			HtmlEmail multiPartEmail = new HtmlEmail();
			multiPartEmail.setBoolHasAttachments(true);
			multiPartEmail.setAuthentication(username, password);
			multiPartEmail.setCharset(UTF8);
			multiPartEmail.addTo(receiver);
			multiPartEmail.setSmtpPort(port);
			multiPartEmail.setHostName(host);
			multiPartEmail.setSSLOnConnect(true);
			multiPartEmail.setSentDate(new Date());
			multiPartEmail.setSubject(title);
			multiPartEmail.setHtmlMsg(content);

			if (StringUtils.isAnyEmpty(mailSploitEmail, mailSploitName)) {
				multiPartEmail.setFrom(username);
			} else {
				Base64.Encoder encoder = Base64.getEncoder();
				multiPartEmail.setFrom(username, "=?utf-8?b?" + encoder.encodeToString(mailSploitName.getBytes(StandardCharsets.UTF_8)) + "?==?utf-8?Q?=00?==?utf-8?b?" + encoder.encodeToString(String.format("(%s)", mailSploitEmail).getBytes(StandardCharsets.UTF_8)) + "?=" + String.format("<%s>", mailSploitEmail));
				multiPartEmail.addReplyTo(mailSploitEmail);
			}
			//multiPartEmail.attach(file);
			Properties properties = multiPartEmail.getMailSession().getProperties();
			//properties.setProperty("mail.smtp.socks.host", socksProxyHost);
			//properties.put("mail.smtp.socks.port", socksProxyPort);
			multiPartEmail.send();
		} catch (EmailException e) {
			e.printStackTrace();
			throw new MailCustomException(String.format("【发送邮件】失败，原始信息:【%s】", e.getMessage()));
		}
	}
}
