package com.github.zj.dreamly.tool.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <h2>CorsProperties</h2>
 *
 * @author: 苍海之南
 * @since: 2021-01-19 10:29
 **/
@Data
@ConfigurationProperties("dreamly.tool.cors")
public class CorsProperties {
	/**
	 * 开启xss
	 */
	private Boolean enabled = true;
}
