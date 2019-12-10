package com.github.zj.dreamly.tool.constant;

/**
 * 系统常量
 *
 * @author 苍海之南
 */
public interface SystemConstant {
	/**
	 * 默认为空消息
	 */
	String DEFAULT_NULL_MESSAGE = "暂无承载数据";
	/**
	 * 默认成功消息
	 */
	String DEFAULT_SUCCESS_MESSAGE = "操作成功";
	/**
	 * 默认失败消息
	 */
	String DEFAULT_FAILURE_MESSAGE = "操作失败";
	/**
	 * 默认未授权消息
	 */
	String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";
	/**
	 * 系统默认核心数
	 */
	int CORE_SIZE = Runtime.getRuntime().availableProcessors();
}
