package com.github.zj.dreamly.mail.service.impl;

import com.github.zj.dreamly.mail.entity.MailConnection;
import com.github.zj.dreamly.mail.entity.MailConnectionConfig;
import com.github.zj.dreamly.mail.entity.MailItem;
import com.github.zj.dreamly.mail.entity.UniversalMail;
import com.github.zj.dreamly.mail.enums.ProxyTypeEnum;
import com.github.zj.dreamly.mail.exception.MailCustomException;
import com.github.zj.dreamly.mail.service.MailService;
import com.github.zj.dreamly.mail.util.EmailParsing;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Message;
import com.sun.mail.pop3.POP3Store;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <h2>Pop3ServiceImpl</h2>
 *
 * @author: zeemoo
 * @since: 0.0.1
 **/
public class Pop3ServiceImpl implements MailService {
	/**
	 * Session properties的键名
	 */
	private static final String PROPS_HOST = "mail.pop3.host";
	private static final String PROPS_PORT = "mail.pop3.port";
	private static final String PROPS_SSL = "mail.pop3.ssl.enable";
	private static final String PROPS_AUTH = "mail.pop3.auth";
	private static final String PROPS_SOCKS_PROXY_HOST = "mail.pop3.socks.host";
	private static final String PROPS_SOCKS_PROXY_PORT = "mail.pop3.socks.port";
	private static final String PROPS_HTTP_PROXY_HOST = "mail.pop3.proxy.host";
	private static final String PROPS_HTTP_PROXY_PORT = "mail.pop3.proxy.port";
	private static final String PROPS_HTTP_PROXY_USER = "mail.pop3.proxy.user";
	private static final String PROPS_HTTP_PROXY_PASSWORD = "mail.pop3.proxy.password";
	/**
	 * POP3只能打开INBOX文件夹，也就是收件箱
	 */
	private static final String FOLDER_INBOX = "INBOX";
	/**
	 * 一次性最多能同步的数量
	 */
	private static final int MAX_SYNCHRO_SIZE = 80;

	/**
	 * 解析邮件
	 *
	 * @param mailItem      需要解析的邮件列表项
	 * @param localSavePath 本地存储路径
	 */
	@Override
	public UniversalMail parseEmail(MailItem mailItem, String localSavePath) throws MailCustomException {
		//使用通用的邮件解析工具类解析邮件
		return EmailParsing.parseMail(mailItem, localSavePath);
	}

	/**
	 * 列举需要被同步的邮件
	 *
	 * @param mailConnection  邮箱连接，也可以做成字段
	 * @param existUids 已存在的邮件uid
	 */
	@Override
	public List<MailItem> listAll(MailConnection mailConnection, List<String> existUids) throws MailCustomException {
		POP3Store pop3Store = mailConnection.getPop3Store();
		try {
			//获取文件夹，POP3只能获取收件箱的邮件
			POP3Folder folder = (POP3Folder) pop3Store.getFolder(FOLDER_INBOX);
			//文件夹必须打开才可以获取邮件
			folder.open(Folder.READ_ONLY);
			Message[] messages = folder.getMessages();
			List<MailItem> mailItems = new ArrayList<>();
			//进行去重筛选需要同步的邮件
			for (int i = messages.length - 1; i >= 0; i--) {
				String uid = folder.getUID(messages[i]);
				if (!existUids.contains(uid)) {
					POP3Message pop3Message = (POP3Message) messages[i];
					mailItems.add(MailItem.builder().pop3Message(pop3Message).build());
				}
				//到一定数量停止
				if (mailItems.size() == MAX_SYNCHRO_SIZE) {
					break;
				}
			}
			return mailItems;
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new MailCustomException(String.format("【POP3服务】打开文件夹/获取邮件列表失败，错误信息【%s】",
				e.getMessage()));
		}
	}

	/**
	 * 连接服务器
	 *
	 * @param mailConnectionConfig 连接配置
	 * @param proxy       是否设置代理
	 * @return 返回连接
	 */
	@Override
	public MailConnection createConn(MailConnectionConfig mailConnectionConfig, boolean proxy) throws MailCustomException {
		//构建Session Properties
		Properties properties = new Properties();
		properties.put(PROPS_HOST, mailConnectionConfig.getHost());
		///properties.put(PROPS_PORT, mailConnCfg.getPort());
		properties.put(PROPS_SSL, mailConnectionConfig.isSsl());
		properties.put(PROPS_AUTH, true);

		//设置代理
		if (proxy && mailConnectionConfig.getProxyType() != null) {
			ProxyTypeEnum proxyType = mailConnectionConfig.getProxyType();
			if (proxyType.equals(ProxyTypeEnum.HTTP)) {
				properties.put(PROPS_HTTP_PROXY_HOST, mailConnectionConfig.getProxyHost());
				properties.put(PROPS_HTTP_PROXY_PORT, mailConnectionConfig.getProxyPort());
				properties.put(PROPS_HTTP_PROXY_USER, mailConnectionConfig.getProxyUsername());
				properties.put(PROPS_HTTP_PROXY_PASSWORD, mailConnectionConfig.getProxyPassword());
			} else if (proxyType.equals(ProxyTypeEnum.SOCKS)) {
				//java mail里socks代理是不支持用户名密码验证的
				properties.put(PROPS_SOCKS_PROXY_HOST, mailConnectionConfig.getSocksProxyHost());
				properties.put(PROPS_SOCKS_PROXY_PORT, mailConnectionConfig.getSocksProxyPort());
			}
		}
		//构建session
		Session session = Session.getInstance(properties);
		try {
			//连接
			Store store = session.getStore("pop3");
			store.connect(mailConnectionConfig.getEmail(), mailConnectionConfig.getPassword());
			return MailConnection.builder().pop3Store((POP3Store) store).build();
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new MailCustomException(e.getMessage());
		}
	}
}
