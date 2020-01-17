package com.github.zj.dreamly.tool.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * <h2>SpringUtil</h2>
 *
 * @author: 苍海之南
 * @since: 2019-07-11 16:45
 **/
@Slf4j
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringUtil.context = context;
	}

	public static <T> T getBean(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		return context.getBean(clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) {
		if (beanId == null) {
			return null;
		}
		return (T) context.getBean(beanId);
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		if (null == beanName || "".equals(beanName.trim())) {
			return null;
		}
		if (clazz == null) {
			return null;
		}
		return (T) context.getBean(beanName, clazz);
	}

	public static ApplicationContext getContext() {
		if (context == null) {
			return null;
		}
		return context;
	}

	public static void publishEvent(ApplicationEvent event) {
		if (context == null) {
			return;
		}
		try {
			context.publishEvent(event);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
}
