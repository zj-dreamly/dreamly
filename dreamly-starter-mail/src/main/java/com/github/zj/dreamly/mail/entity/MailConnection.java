package com.github.zj.dreamly.mail.entity;

import com.sun.mail.imap.IMAPStore;
import com.sun.mail.pop3.POP3Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import microsoft.exchange.webservices.data.core.ExchangeService;

/**
 * <h2>MailConnection</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.1
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailConnection {

	private POP3Store pop3Store;
	private IMAPStore imapStore;
	private ExchangeService exchangeService;
}
