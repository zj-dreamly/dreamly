package com.github.zj.dreamly.simple.security.security;

import com.github.zj.dreamly.simple.security.annotation.support.PreAuthorizeAspect;
import com.github.zj.dreamly.simple.security.el.PreAuthorizeExpressionRoot;
import com.github.zj.dreamly.simple.security.jwt.JwtOperator;
import com.github.zj.dreamly.simple.security.jwt.UserOperator;
import com.github.zj.dreamly.simple.security.spec.Spec;
import com.github.zj.dreamly.simple.security.spec.SpecRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 配置类
 *
 * @author 苍海之南
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@AutoConfigureBefore(SecurityAutoConfiguration.class)
@SuppressWarnings("all")
public class SecurityConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public JwtOperator jwtOperator(SecurityProperties securityProperties) {
		return new JwtOperator(securityProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public UserOperator userOperator(JwtOperator jwtOperator) {
		return new UserOperator(jwtOperator);
	}

	@Bean
	@ConditionalOnMissingBean
	public PreAuthorizeExpressionRoot preAuthorizeExpressionRoot(UserOperator userOperator) {
		return new PreAuthorizeExpressionRoot(userOperator);
	}

	@Bean
	@ConditionalOnMissingBean
	public PreAuthorizeAspect preAuthorizeAspect(PreAuthorizeExpressionRoot preAuthorizeExpressionRoot) {
		return new PreAuthorizeAspect(preAuthorizeExpressionRoot);
	}

	@Bean
	@ConditionalOnBean(SpecRegistry.class)
	public List<Spec> specListFromSpecRegistry(SpecRegistry specRegistry) {
		List<Spec> specList = specRegistry.getSpecList();
		if (CollectionUtils.isEmpty(specList)) {
			throw new IllegalArgumentException("specList cannot be empty.");
		}
		return specList;
	}

	@Bean
	@ConditionalOnMissingBean(SpecRegistry.class)
	public List<Spec> specListFromProperties(SecurityProperties securityProperties) {
		return securityProperties.getSpecList();
	}
}
