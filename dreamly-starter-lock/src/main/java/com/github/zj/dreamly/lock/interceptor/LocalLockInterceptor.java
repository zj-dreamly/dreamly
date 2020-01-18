package com.github.zj.dreamly.lock.interceptor;

import com.github.zj.dreamly.lock.annotation.LocalLock;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * <h2>LocalLockInterceptor</h2>
 *
 * @author: 苍海之南
 * @since: 2020-01-18 17:45
 **/
@Aspect
@Configuration
public class LocalLockInterceptor {

	private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
		.maximumSize(Integer.MAX_VALUE)
		.expireAfterWrite(10, TimeUnit.SECONDS)
		.build();

	@Around("execution(public * *(..)) &&" +
		" @annotation(com.github.zj.dreamly.lock.annotation.LocalLock)")
	public Object interceptor(ProceedingJoinPoint pjp) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		LocalLock localLock = method.getAnnotation(LocalLock.class);
		String key = getKey(localLock.key(), pjp.getArgs());
		if (!StringUtils.isEmpty(key)) {
			if (CACHES.getIfPresent(key) != null) {
				throw new RuntimeException("请勿重复请求");
			}
			// 如果是第一次请求,就将 key 当前对象压入缓存中
			CACHES.put(key, key);
		}
		try {
			return pjp.proceed();
		} catch (Throwable throwable) {
			throw new RuntimeException("服务器异常");
		} finally {
			CACHES.invalidate(key);
		}
	}

	/**
	 * key 的生成策略
	 *
	 * @param keyExpress 表达式
	 * @param args       参数
	 * @return 生成的key
	 */
	private String getKey(String keyExpress, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			keyExpress = keyExpress.replace("arg[" + i + "]", args[i].toString());
		}
		return keyExpress;
	}

}
