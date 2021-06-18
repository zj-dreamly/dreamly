package com.github.zj.dreamly.oss.properties;

import cn.hutool.core.lang.Dict;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: zj
 * @since: 2021-06-18 17:20
 **/
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

	/**
	 * 是否启用
	 */
	private Boolean enabled;

	/**
	 * 对象存储名称
	 */
	private String name;

	/**
	 * 是否开启租户模式
	 */
	private Boolean tenantMode;

	/**
	 * 对象存储服务的URL
	 */
	private String endpoint;

	/**
	 * Access key就像用户ID，可以唯一标识你的账户
	 */
	private String accessKey;

	/**
	 * Secret key是你账户的密码
	 */
	private String secretKey;

	/**
	 * 默认的存储桶名称
	 */
	private String bucketName = "bucket";
	/**
	 * 自定义属性
	 */
	private Dict args;
}

