package com.github.zj.dreamly.mail.entity;

import com.github.zj.dreamly.mail.enums.ProxyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h2>MailConnectionConfig</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.1
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailConnectionConfig {
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 服务器地址
	 */
	private String host;
	/**
	 * 端口号
	 */
	private Integer port;
	/**
	 * 是否使用加密方式传输
	 */
	private boolean ssl;

	/**
	 * 代理类型
	 */
	private ProxyTypeEnum proxyType;
	/**
	 * HTTP代理地址
	 */
	private String proxyHost;
	/**
	 * HTTP代理端口
	 */
	private Integer proxyPort;
	/**
	 * HTTP代理用户名
	 */
	private String proxyUsername;
	/**
	 * HTTP代理密码
	 */
	private String proxyPassword;
	/**
	 * Socks代理端口
	 */
	private String socksProxyHost;
	/**
	 * socks代理端口
	 */
	private Integer socksProxyPort;

	public MailConnectionConfig(String email, String password, String host, Integer port) {
		this.email = email;
		this.password = password;
		this.host = host;
		this.port = port;
	}
}
