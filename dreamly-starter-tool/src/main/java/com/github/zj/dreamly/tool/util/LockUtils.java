package com.github.zj.dreamly.tool.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h2>LockUtils</h2>
 * <p>
 * 使用场景：
 * <p>
 * 用于保证短时间内客户端发起的多次重复请求的幂等，仅适用于单机环境
 * <p>
 * 调用方传入参数来控制锁的成功与失败，在方法调用完毕后需主动释放锁
 *
 * @author: 苍海之南
 * @since: 0.0.5
 **/
@Slf4j
public class LockUtils {

	private static Map<String, Thread> threadMap = new ConcurrentHashMap<>();

	/**
	 * 尝试获取锁
	 * 参数锁，相同参数串行，不同参数并行
	 *
	 * @param param 传入的参数（IP，PHONE等唯一属性字段）
	 * @return 是否成功拿到锁
	 */
	public static synchronized Boolean tryLock(String param) {
		if (StringUtils.isEmpty(param)) {
			throw new IllegalArgumentException("参数错误");
		}

		final Thread thread = threadMap.get(param);
		if (null == thread) {
			threadMap.put(param, Thread.currentThread());
			return true;
		}

		return false;
	}

	/**
	 * 释放锁
	 * 参数锁，相同参数串行，不同参数并行
	 *
	 * @param param 传入的参数（IP，PHONE等唯一属性字段）
	 */
	public static synchronized void unlock(String param) {
		if (StringUtils.isEmpty(param)) {
			throw new IllegalArgumentException("参数错误");
		}

		final Thread thread = threadMap.get(param);
		if (thread == Thread.currentThread()) {
			threadMap.remove(param);
		}
	}
}
