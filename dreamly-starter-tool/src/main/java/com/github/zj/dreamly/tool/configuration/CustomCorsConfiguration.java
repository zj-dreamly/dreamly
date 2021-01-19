package com.github.zj.dreamly.tool.configuration;

import com.github.zj.dreamly.tool.properties.CorsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author 苍海之南
 */
@Configuration
@ConditionalOnProperty(value = "dreamly.tool.cors.enabled", havingValue = "true")
@EnableConfigurationProperties({CorsProperties.class})
public class CustomCorsConfiguration {
	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		// 设置允许的请求地址
		corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
		// 设置允许的请求头
		corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
		// 设置允许的请求方法
		corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
		// 设置缓存时间（即直接接受请求，无需再发送opinion）
		corsConfiguration.setMaxAge(7200L);
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}
}
