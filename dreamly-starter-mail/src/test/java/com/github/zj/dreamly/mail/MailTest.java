package com.github.zj.dreamly.mail;

import com.github.zj.dreamly.mail.constant.MailConstant;
import com.github.zj.dreamly.mail.entity.MailConnection;
import com.github.zj.dreamly.mail.entity.MailConnectionConfig;
import com.github.zj.dreamly.mail.entity.MailItem;
import com.github.zj.dreamly.mail.exception.MailCustomException;
import com.github.zj.dreamly.mail.service.MailService;
import com.github.zj.dreamly.mail.service.impl.Pop3ServiceImpl;
import com.github.zj.dreamly.mail.util.EmailParsing;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>MailTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-04 17:07
 **/
public class MailTest {
	@Test
	public void receive() throws MailCustomException {

		MailService mailService = new Pop3ServiceImpl();

		MailConnectionConfig mailConnectionConfig = new MailConnectionConfig();
		mailConnectionConfig.setEmail("ju.zhou@synconize.com");
		mailConnectionConfig.setPassword("123456");
		mailConnectionConfig.setHost("smtp.exmail.qq.com");
		mailConnectionConfig.setPort(465);
		mailConnectionConfig.setSsl(true);
		final MailConnection conn = mailService.createConn(mailConnectionConfig, false);

		final List<MailItem> mailItems = mailService.listAll(conn, new ArrayList<>());
		for (MailItem mailItem : mailItems) {
			System.out.println(EmailParsing.parseMail(mailItem, MailConstant.LOCAL_SAVE_PATH));
			return;
		}

	}

	@Test
	public void received() throws Exception {
	}
}
