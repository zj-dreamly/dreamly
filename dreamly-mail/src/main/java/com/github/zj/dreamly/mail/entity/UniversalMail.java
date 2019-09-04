package com.github.zj.dreamly.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <h2>UniversalMail</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-04 16:59
 **/
@Data
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
}

