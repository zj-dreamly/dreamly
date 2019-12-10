package com.github.zj.dreamly.mail.entity;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * <h2>UniversalMail</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.1
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversalMail {

	private String uid;
	private String fromer;
	private String receiver;
	private String bcc;
	private String cc;
	private String folder;
	private Boolean hasRead;
	private Boolean hasAttachment;
	private Date sendDate;
	private String title;
	private String emlPath;
	private String content;
	private String email;
	private List<UniversalAttachment> attachments;

	@Override
	public String toString() {
		return "【通用邮件解析结果】{" +
			"【邮件id】：'" + uid + '\n' +
			"【发件人】：'" + fromer + '\n' +
			"【收件人】：'" + receiver + '\n' +
			"【密送人】：'" + bcc + '\n' +
			"【抄送人】" + cc + '\n' +
			"【文件夹】：'" + folder + '\n' +
			"【是否已读】：" + hasRead + '\n' +
			"【是否存在附件】：" + hasAttachment + '\n' +
			"【发送时间】：" + sendDate + '\n' +
			"【本地存储邮件地址】：'" + emlPath + '\'' + '\n' +
			"【邮件主题】：'" + title + '\'' + '\n' +
			"【邮件内容】：'" + content + '\'' + '\n' +
			"【email】：'" + email + '\'' + '\n' +
			"【附件信息】：" + attachments + '\n' +
			'}';
	}
}

