package com.github.zj.dreamly.mail.service.impl;

import com.github.zj.dreamly.mail.entity.MailConnection;
import com.github.zj.dreamly.mail.entity.MailConnectionConfig;
import com.github.zj.dreamly.mail.entity.MailItem;
import com.github.zj.dreamly.mail.entity.UniversalMail;
import com.github.zj.dreamly.mail.exception.MailCustomException;
import com.github.zj.dreamly.mail.service.MailService;
import com.github.zj.dreamly.mail.util.EmailParsing;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.WebProxy;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.SortDirection;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.credential.WebProxyCredentials;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>ExchangeServiceImpl</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-04 17:02
 **/
public class ExchangeServiceImpl implements MailService {

	/**
	 * 最大同步数量
	 */
	private static final int MAX_SYNCHRO_SIZE = 80;

	/**
	 * 解析邮件
	 */
	@Override
	public UniversalMail parseEmail(MailItem mailItem, String localSavePath) throws MailCustomException {
		return EmailParsing.parseMail(mailItem, localSavePath);
	}

	/**
	 * 列举需要被同步的邮件
	 */
	@Override
	public List<MailItem> listAll(MailConnection mailConnection, List<String> existUids) throws MailCustomException {
		ExchangeService exchangeService = mailConnection.getExchangeService();
		try {
			Folder msgFolderRoot = Folder.bind(exchangeService, WellKnownFolderName.MsgFolderRoot);
			int childFolderCount = msgFolderRoot.getChildFolderCount();
			FolderView folderView = new FolderView(childFolderCount);
			FindFoldersResults folders = msgFolderRoot.findFolders(folderView);
			ArrayList<Folder> folderList = folders.getFolders();
			List<MailItem> mailItems = new ArrayList<>();
			//判断是否达到一定数量的标志（使用双层循环）
			boolean flag = false;
			for (Folder folder : folderList) {
				String displayName = folder.getDisplayName();
				//排除已知的非邮件格式的文件夹（是EmailMessage类型但是不是标准邮件）
				if (
					"Files".equals(displayName)
						|| "文件".equals(displayName)
						|| "檔案".equals(displayName)
						|| "記事".equals(displayName)
						|| displayName.startsWith("Conservation")
				) {
					continue;
				}
				if (folder.getTotalCount() > 0) {
					ItemView itemView = new ItemView(folder.getTotalCount());
					itemView.getOrderBy().add(ItemSchema.DateTimeReceived, SortDirection.Descending);
					FindItemsResults<Item> items = null;
					try {
						items = exchangeService.findItems(folder.getId(), itemView);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					ArrayList<Item> itemList = items.getItems();
					for (Item item :
						itemList) {
						if (item instanceof EmailMessage && !existUids.contains(item.getId().getUniqueId())) {
							EmailMessage message = (EmailMessage) item;
							mailItems.add(MailItem.builder().exchangeMessage(message).build());
						}
						flag = mailItems.size() >= MAX_SYNCHRO_SIZE;
						if (flag) {
							break;
						}
					}
					if (flag) {
						break;
					}
				}
			}
			return mailItems;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MailCustomException(e.getMessage());
		}
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
		ExchangeService service = new ExchangeService();
		//配置代理
		if (proxy) {
			WebProxy webProxy = new WebProxy(
				mailConnectionConfig.getHost()
				, mailConnectionConfig.getProxyPort()
				, new WebProxyCredentials(
				mailConnectionConfig.getProxyUsername()
				, mailConnectionConfig.getProxyPassword()
				, ""
			)
			);
			service.setWebProxy(webProxy);
		}
		service.setCredentials(
			new WebCredentials(
				mailConnectionConfig.getEmail()
				, mailConnectionConfig.getPassword()
			)
		);
		//设置超时时间，在拉取邮件的时候保证不中断
		service.setTimeout(600000);
		try {
			if (mailConnectionConfig.isSsl()) {
				//我怀疑exchange的都是https方式
				service.autodiscoverUrl(mailConnectionConfig.getEmail(),
					redirectionUrl -> redirectionUrl.toLowerCase().startsWith("https://"));
			} else {
				service.autodiscoverUrl(mailConnectionConfig.getEmail());
			}
			mailConnectionConfig.setHost(service.getUrl().getHost());
			return MailConnection.builder().exchangeService(service).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MailCustomException(e.getMessage());
		}
	}
}
