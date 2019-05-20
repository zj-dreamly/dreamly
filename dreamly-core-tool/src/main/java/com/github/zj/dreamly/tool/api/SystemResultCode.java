package com.github.zj.dreamly.tool.api;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务代码枚举
 *
 * @author 苍海之南
 */
@Getter
@AllArgsConstructor
public enum SystemResultCode implements IResultCode {

	/**
	 * 操作成功 code：200
	 */
	SUCCESS(HttpStatus.HTTP_OK, "操作成功"),

	/**
	 * 业务异常 code：400
	 */
	FAILURE(HttpStatus.HTTP_BAD_REQUEST, "业务异常"),

	/**
	 * 请求未授权 code：401
	 */
	UN_AUTHORIZED(HttpStatus.HTTP_UNAUTHORIZED, "请求未授权"),

	/**
	 * 客户端请求未授权 code：401
	 */
	CLIENT_UN_AUTHORIZED(HttpStatus.HTTP_UNAUTHORIZED, "客户端请求未授权"),

	/**
	 * 404 没找到请求 code：404
	 */
	NOT_FOUND(HttpStatus.HTTP_NOT_FOUND, "404 没找到请求"),

	/**
	 * 消息不能读取 code：400
	 */
	MSG_NOT_READABLE(HttpStatus.HTTP_BAD_REQUEST, "消息不能读取"),

	/**
	 * 不支持当前请求方法 code：405
	 */
	METHOD_NOT_SUPPORTED(HttpStatus.HTTP_BAD_METHOD, "不支持当前请求方法"),

	/**
	 * 不支持当前媒体类型 code：415
	 */
	MEDIA_TYPE_NOT_SUPPORTED(HttpStatus.HTTP_UNSUPPORTED_TYPE, "不支持当前媒体类型"),

	/**
	 * 请求被拒绝 code：403
	 */
	REQ_REJECT(HttpStatus.HTTP_FORBIDDEN, "请求被拒绝"),

	/**
	 * 服务器异常 code：500
	 */
	INTERNAL_SERVER_ERROR(HttpStatus.HTTP_INTERNAL_ERROR, "服务器异常"),

	/**
	 * 缺少必要的请求参数 code：400
	 */
	PARAM_MISS(HttpStatus.HTTP_BAD_REQUEST, "缺少必要的请求参数"),

	/**
	 * 请求参数类型错误 code：400
	 */
	PARAM_TYPE_ERROR(HttpStatus.HTTP_BAD_REQUEST, "请求参数类型错误"),

	/**
	 * 请求参数绑定错误 code：400
	 */
	PARAM_BIND_ERROR(HttpStatus.HTTP_BAD_REQUEST, "请求参数绑定错误"),

	/**
	 * 参数校验失败 code：400
	 */
	PARAM_VALID_ERROR(HttpStatus.HTTP_BAD_REQUEST, "参数校验失败"),
	;

	/**
	 * code编码
	 */
	final int code;
	/**
	 * 中文信息描述
	 */
	final String message;
}
