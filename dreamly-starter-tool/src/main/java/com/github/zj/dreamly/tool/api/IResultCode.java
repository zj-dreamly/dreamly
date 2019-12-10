package com.github.zj.dreamly.tool.api;

import java.io.Serializable;

/**
 * 业务代码接口
 *
 * @author 苍海之南
 */
public interface IResultCode extends Serializable {
	/**
	 * 获取消息
	 *
	 * @return message
	 */
	String getMessage();

	/**
	 * 获取状态码
	 *
	 * @return code
	 */
	int getCode();

}
