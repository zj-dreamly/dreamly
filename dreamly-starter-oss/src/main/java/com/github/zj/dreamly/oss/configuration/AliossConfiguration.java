package com.github.zj.dreamly.oss.configuration;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.github.zj.dreamly.oss.AliossTemplate;
import com.github.zj.dreamly.oss.properties.OssProperties;
import com.github.zj.dreamly.oss.rule.DreamlyOssRule;
import com.github.zj.dreamly.oss.rule.OssRule;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Alioss配置类
 *
 * @author Chill
 */
@Configuration
@AllArgsConstructor
@AutoConfigureAfter(QiniuConfiguration.class)
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(value = "oss.name" , havingValue = "alioss")
public class AliossConfiguration {

	private OssProperties ossProperties;

	@Bean
	@ConditionalOnMissingBean(OssRule.class)
	public OssRule ossRule() {
		return new DreamlyOssRule();
	}

	@Bean
	@ConditionalOnMissingBean(OSSClient.class)
	public OSSClient ossClient() {
		// 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
		ClientConfiguration conf = new ClientConfiguration();
		// 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
		conf.setMaxConnections(1024);
		// 设置Socket层传输数据的超时时间，默认为50000毫秒。
		conf.setSocketTimeout(50000);
		// 设置建立连接的超时时间，默认为50000毫秒。
		conf.setConnectionTimeout(50000);
		// 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
		conf.setConnectionRequestTimeout(1000);
		// 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
		conf.setIdleConnectionTime(60000);
		// 设置失败请求重试次数，默认为3次。
		conf.setMaxErrorRetry(5);
		CredentialsProvider credentialsProvider = new DefaultCredentialProvider(ossProperties.getAccessKey(), ossProperties.getSecretKey());
		return new OSSClient(ossProperties.getEndpoint(), credentialsProvider, conf);
	}

	@Bean
	@ConditionalOnMissingBean(AliossTemplate.class)
	@ConditionalOnBean({OSSClient.class, OssRule.class})
	public AliossTemplate aliossTemplate(OSSClient ossClient, OssRule ossRule) {
		return new AliossTemplate(ossClient, ossProperties, ossRule);
	}

}
