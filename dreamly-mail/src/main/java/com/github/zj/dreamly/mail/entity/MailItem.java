package com.github.zj.dreamly.mail.entity;

import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.pop3.POP3Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;

/**
 * <h2>MailItem</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-04 16:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailItem {
	private IMAPMessage imapMessage;
	private POP3Message pop3Message;
	private EmailMessage exchangeMessage;
}
