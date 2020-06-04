package com.github.zj.dreamly.mail.service.impl;

import com.github.zj.dreamly.mail.entity.MailConnection;
import com.github.zj.dreamly.mail.entity.MailConnectionConfig;
import com.github.zj.dreamly.mail.entity.MailItem;
import com.github.zj.dreamly.mail.entity.UniversalMail;
import com.github.zj.dreamly.mail.enums.ProxyTypeEnum;
import com.github.zj.dreamly.mail.exception.MailCustomException;
import com.github.zj.dreamly.mail.service.MailService;
import com.github.zj.dreamly.mail.util.EmailParsing;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.IMAPStore;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <h2>ImapServiceImpl</h2>
 *
 * @author: zeemoo
 * @since: 0.0.1
 **/
public class ImapServiceImpl implements MailService {

	/**
	 * Session properties的键名
	 */
	private static final String PROPS_HOST = "mail.imap.host";
	private static final String PROPS_PORT = "mail.imap.port";
	private static final String PROPS_SSL = "mail.imap.ssl.enable";
	private static final String PROPS_AUTH = "mail.imap.auth";
	private static final String PROPS_SOCKS_PROXY_HOST = "mail.imap.socks.host";
	private static final String PROPS_SOCKS_PROXY_PORT = "mail.imap.socks.port";
	private static final String PROPS_HTTP_PROXY_HOST = "mail.imap.proxy.host";
	private static final String PROPS_HTTP_PROXY_PORT = "mail.imap.proxy.port";
	private static final String PROPS_HTTP_PROXY_USER = "mail.imap.proxy.user";
	private static final String PROPS_HTTP_PROXY_PASSWORD = "mail.imap.proxy.password";
	private static final String PROPS_PARTIALFETCH = "mail.imap.partialfetch";
	private static final String PROPS_STARTTLS = "mail.imap.starttls.enable";
	/**
	 * 一次性最多同步的邮件数量
	 */
	private static final int MAX_SYNCHRO_SIZE = 100;

	/**
	 * 解析邮件
	 *
	 * @param mailItem      邮箱列表项
	 * @param localSavePath 本地存储路径
	 */
	@Override
	public UniversalMail parseEmail(MailItem mailItem, String localSavePath) throws MailCustomException {
		return EmailParsing.parseMail(mailItem, localSavePath);
	}

	/**
	 * 列举需要被同步的邮件
	 *
	 * @param mailConnection  邮箱连接，可以做成这个类的字段
	 * @param existUids 已同步的邮件uid
	 */
	@Override
	public List<MailItem> listAll(MailConnection mailConnection, List<String> existUids) throws MailCustomException {
		IMAPStore imapStore = mailConnection.getImapStore();
		try {
			Folder defaultFolder = imapStore.getDefaultFolder();
			List<MailItem> mailItems = new ArrayList<>();
			Folder[] list = defaultFolder.list();
			//判断是否达到一定数量的标志（使用双层循环）
			boolean flag = false;
			for (Folder folder : list) {
				IMAPFolder imapFolder = (IMAPFolder) folder;
				//Gmail额外分层
				if ("[gmail]".equalsIgnoreCase(imapFolder.getName())) {
					flag = listGmailMessageFolder(mailItems, existUids, imapFolder);
				} else {
					flag = listFolderMessage(mailItems, existUids, imapFolder);
				}
				//已达到数目，直接退出循环
				if (flag) {
					break;
				}
			}
			return mailItems;
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new MailCustomException(String.format("【IMAP服务】打开文件夹/获取邮件列表失败，错误信息【%s】",
				e.getMessage()));
		}
	}

	/**
	 * Gmail邮箱有额外的一层文件夹，需要被再打开一次
	 *
	 * @param target     存储需要被同步的邮件列表项
	 * @param existUids  已同步下来的邮件uid
	 * @param imapFolder 有邮件的文件夹
	 */
	private boolean listGmailMessageFolder(List<MailItem> target, List<String> existUids, IMAPFolder imapFolder) throws MessagingException {
		Folder[] list = imapFolder.list();
		boolean flag = false;
		for (Folder folder :
			list) {
			flag = listFolderMessage(target, existUids, (IMAPFolder) folder);
			if (flag) {
				break;
			}
		}
		return flag;
	}

	/**
	 * 通用的获取文件夹下邮件代码
	 *
	 * @param target     存储需要被同步的邮件列表项
	 * @param existUids  已同步下来的邮件uid
	 * @param imapFolder 有邮件的文件夹
	 */
	private boolean listFolderMessage(List<MailItem> target, List<String> existUids, IMAPFolder imapFolder) throws MessagingException {
		boolean flag = false;
		imapFolder.open(Folder.READ_ONLY);
		Message[] messages = imapFolder.getMessages();
		for (int j = messages.length - 1; j >= 0; j--) {
			if (!existUids.contains(imapFolder.getFullName() + imapFolder.getUID(messages[j]))) {
				target.add(MailItem.builder().imapMessage((IMAPMessage) messages[j]).build());
			}
			flag = target.size() == MAX_SYNCHRO_SIZE;
			if (flag) {
				break;
			}
		}
		return flag;
	}

	/**
	 * 连接服务器
	 *
	 * @param mailConnectionConfig 连接配置
	 * @param proxy       是否代理
	 * @return 返回连接
	 */
	@Override
	public MailConnection createConn(MailConnectionConfig mailConnectionConfig, boolean proxy) throws MailCustomException {
		//构建Session Properties
		Properties properties = new Properties();
		properties.put(PROPS_HOST, mailConnectionConfig.getHost());
		///properties.put(PROPS_PORT, mailConnCfg.getPort());
		properties.put(PROPS_SSL, mailConnectionConfig.isSsl());
		properties.put(PROPS_PARTIALFETCH, false);
		properties.put(PROPS_STARTTLS, false);
		properties.put(PROPS_AUTH, true);
		//设置代理
		if (proxy && mailConnectionConfig.getProxyType() != null) {
			ProxyTypeEnum proxyType = mailConnectionConfig.getProxyType();
			if (proxyType.equals(ProxyTypeEnum.SOCKS)) {
				properties.put(PROPS_SOCKS_PROXY_HOST, mailConnectionConfig.getSocksProxyHost());
				properties.put(PROPS_SOCKS_PROXY_PORT, mailConnectionConfig.getSocksProxyPort());
			} else if (proxyType.equals(ProxyTypeEnum.HTTP)) {
				properties.put(PROPS_HTTP_PROXY_HOST, mailConnectionConfig.getProxyHost());
				properties.put(PROPS_HTTP_PROXY_PORT, mailConnectionConfig.getProxyPort());
				properties.put(PROPS_HTTP_PROXY_USER, mailConnectionConfig.getProxyUsername());
				properties.put(PROPS_HTTP_PROXY_PASSWORD, mailConnectionConfig.getProxyPassword());
			}
		}
		//构建session
		Session session = Session.getInstance(properties);
		try {
			//连接
			Store store = session.getStore("imap");
			store.connect(mailConnectionConfig.getEmail(), mailConnectionConfig.getPassword());
			return MailConnection.builder().imapStore((IMAPStore) store).build();
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new MailCustomException(e.getMessage());
		}
	}
}
