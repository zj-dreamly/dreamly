package com.github.zj.dreamly.mail.service;

import com.github.zj.dreamly.mail.entity.MailConnection;
import com.github.zj.dreamly.mail.entity.MailConnectionConfig;
import com.github.zj.dreamly.mail.entity.MailItem;
import com.github.zj.dreamly.mail.entity.UniversalMail;
import com.github.zj.dreamly.mail.exception.MailCustomException;

import java.util.List;

/**
 * <h2>MailService</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-04 17:02
 **/
public interface MailService {
	/**
	 * 解析邮件
	 *
	 * @param mailItem      mailItem
	 * @param localSavePath 本地路径
	 * @return {@link UniversalMail}
	 * @throws MailCustomException see {@link MailCustomException}
	 */
	UniversalMail parseEmail(MailItem mailItem, String localSavePath) throws MailCustomException;

	/**
	 * 列举需要被同步的邮件
	 *
	 * @param mailConnection  mailConn
	 * @param existUids existUids
	 * @return {@link MailItem}
	 * @throws MailCustomException see {@link MailCustomException}
	 */
	List<MailItem> listAll(MailConnection mailConnection, List<String> existUids) throws MailCustomException;

	/**
	 * 连接服务器
	 *
	 * @param mailConnectionConfig 连接配置
	 * @param proxy       是否代理
	 * @return 返回连接
	 * @throws MailCustomException see {@link MailCustomException}
	 */
	MailConnection createConn(MailConnectionConfig mailConnectionConfig, boolean proxy) throws MailCustomException;
}
