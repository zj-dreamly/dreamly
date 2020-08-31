package com.github.zj.dreamly.tool.util;

import org.springframework.util.Assert;

import java.io.File;

/**
 * <h2>ToolUtils</h2>
 *
 * @author: 苍海之南
 * @since: 2020-08-13 17:12
 **/
public class ToolUtils {
	/**
	 * 获取文件后缀名
	 * @param fullName 文件全名
	 * @return {String}
	 */
	public static String getFileExtension(String fullName) {
		Assert.notNull(fullName, "file fullName is null.");
		String fileName = new File(fullName).getName();
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}

	/**
	 * 创建StringBuilder对象
	 *
	 * @param sb   初始StringBuilder
	 * @param strs 初始字符串列表
	 * @return StringBuilder对象
	 */
	public static StringBuilder appendBuilder(StringBuilder sb, CharSequence... strs) {
		for (CharSequence str : strs) {
			sb.append(str);
		}
		return sb;
	}
}
