package com.github.zj.dreamly.tool.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 苍海之南
 * @since 0.0.3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQuery {
	/**
	 * 当前页
	 */
	@NotNull(message = "当前页不能为空")
	@Min(value = 1, message = "current最低需要传1")
	private Long current;

	/**
	 * 页面大小
	 */
	@NotNull(message = "页面大小不能为空")
	@Min(value = 1, message = "size最低需要传1")
	private Long size;
}
