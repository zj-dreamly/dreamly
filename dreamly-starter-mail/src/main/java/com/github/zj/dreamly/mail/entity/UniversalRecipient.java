package com.github.zj.dreamly.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h2>UniversalRecipient</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.1
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniversalRecipient {
	private String name;
	private String email;
}

