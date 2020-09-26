/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.zj.dreamly.tool.api;

import cn.hutool.http.HttpStatus;
import com.github.zj.dreamly.tool.constant.SystemConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 *
 * @author Chill
 */
@Data
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
	 * 返回ResponseEntity
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> data(T data) {
		return data(data, SystemConstant.DEFAULT_SUCCESS_MESSAGE);
	}

	/**
	 * 返回ResponseEntity
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
	 * 返回ResponseEntity
	 *
	 * @param code 状态码
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> data(int code, T data, String msg) {
		return new ResponseEntity<>(code, data, data == null ? SystemConstant.DEFAULT_NULL_MESSAGE : msg);
	}

	/**
	 * 返回ResponseEntity
	 *
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> success(String msg) {
		return new ResponseEntity<>(SystemResultCode.SUCCESS, msg);
	}

	/**
	 * 返回ResponseEntity
	 *
	 * @param resultCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> success(IResultCode resultCode) {
		return new ResponseEntity<>(resultCode);
	}

	/**
	 * 返回ResponseEntity
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
	 * 返回ResponseEntity
	 *
	 * @param msg 消息
	 * @param <T> T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> fail(String msg) {
		return new ResponseEntity<>(SystemResultCode.FAILURE, msg);
	}

	/**
	 * 返回ResponseEntity
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
	 * 返回ResponseEntity
	 *
	 * @param resultCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> fail(IResultCode resultCode) {
		return new ResponseEntity<>(resultCode);
	}

	/**
	 * 返回ResponseEntity
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
	 * 返回ResponseEntity
	 *
	 * @param flag 成功状态
	 * @param <T>  T 泛型标记
	 * @return ResponseEntity
	 */
	public static <T> ResponseEntity<T> status(boolean flag) {
		return flag ? success(SystemConstant.DEFAULT_SUCCESS_MESSAGE) : fail(SystemConstant.DEFAULT_FAILURE_MESSAGE);
	}
}
