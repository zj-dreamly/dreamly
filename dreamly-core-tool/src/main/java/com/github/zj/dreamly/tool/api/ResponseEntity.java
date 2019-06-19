package com.github.zj.dreamly.tool.api;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.github.zj.dreamly.tool.constant.DreamlyConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

/**
 * 统一API响应结果封装
 *
 * @author 苍海之南
 */
@Getter
@Setter
@ToString
@ApiModel(description = "返回信息")
@NoArgsConstructor
@SuppressWarnings("all")
public class ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "状态码", required = true)
	private int code;
	@ApiModelProperty(value = "是否成功", required = true)
	private boolean success;
	@ApiModelProperty(value = "承载数据")
	private T data;
	@ApiModelProperty(value = "返回消息", required = true)
	private String msg;

	private ResponseEntity(IResultCode resultCode) {
		this(resultCode, null, resultCode.getMessage());
	}

	private ResponseEntity(IResultCode resultCode, String msg) {
		this(resultCode, null, msg);
	}

	private ResponseEntity(IResultCode resultCode, T data) {
		this(resultCode, data, resultCode.getMessage());
	}

	private ResponseEntity(IResultCode resultCode, T data, String msg) {
		this(resultCode.getCode(), data, msg);
	}

	private ResponseEntity(int code, T data, String msg) {
		this.code = code;
		this.data = data;
		this.msg = msg;
		this.success = SystemResultCode.SUCCESS.code == code;
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	private static boolean isSuccess(@Nullable ResponseEntity<?> result) {
		return Optional.ofNullable(result)
			.map(x -> ObjectUtil.equal(SystemResultCode.SUCCESS.code, x.code))
			.orElse(Boolean.FALSE);
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isNotSuccess(@Nullable ResponseEntity<?> result) {
		return !ResponseEntity.isSuccess(result);
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> data(T data) {
		return data(data, DreamlyConstant.DEFAULT_SUCCESS_MESSAGE);
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> data(T data, String msg) {
		return data(HttpStatus.HTTP_OK, data, msg);
	}

	/**
	 * 返回R
	 *
	 * @param code 状态码
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> data(int code, T data, String msg) {
		return new ResponseEntity<>(code, data, data == null ? DreamlyConstant.DEFAULT_NULL_MESSAGE : msg);
	}

	/**
	 * 返回R
	 *
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> success(String msg) {
		return new ResponseEntity<>(SystemResultCode.SUCCESS, msg);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> success(IResultCode resultCode) {
		return new ResponseEntity<>(resultCode);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param msg        消息
	 * @param <T>        T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> success(IResultCode resultCode, String msg) {
		return new ResponseEntity<>(resultCode, msg);
	}

	/**
	 * 返回R
	 *
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> fail(String msg) {
		return new ResponseEntity<>(SystemResultCode.FAILURE, msg);
	}

	/**
	 * 返回R
	 *
	 * @param code 状态码
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> fail(int code, String msg) {
		return new ResponseEntity<>(code, null, msg);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> fail(IResultCode resultCode) {
		return new ResponseEntity<>(resultCode);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param msg        消息
	 * @param <T>        T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> fail(IResultCode resultCode, String msg) {
		return new ResponseEntity<>(resultCode, msg);
	}

	/**
	 * 返回R
	 *
	 * @param flag 成功状态
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> status(boolean flag) {
		return flag ? success(DreamlyConstant.DEFAULT_SUCCESS_MESSAGE) : fail(DreamlyConstant.DEFAULT_FAILURE_MESSAGE);
	}
}
