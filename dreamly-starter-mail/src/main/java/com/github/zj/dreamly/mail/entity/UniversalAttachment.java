package com.github.zj.dreamly.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h2>UniversalAttachment</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.1
 **/
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UniversalAttachment {
	private String path;
	private String cid;
	private String name;
	private String contentType;
}
