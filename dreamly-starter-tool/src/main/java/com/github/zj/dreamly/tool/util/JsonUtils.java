package com.github.zj.dreamly.tool.util;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 目前hutool工具类对json支持并不好，因此有了此类
 *
 * <h2>JsonUtil</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.3
 **/
public class JsonUtils {
	/**
	 * 默认日期格式（年月日时分秒）
	 */
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * json字符串转对象
	 *
	 * @param str   字符串
	 * @param clazz 需要转成想要的对象
	 * @param <T>   返回相应对象
	 * @return 返回相应对象
	 */
	public static <T> T toJavaObject(String str, Class<T> clazz) {
		return JSON.parseObject(str, clazz);
	}

	/**
	 * 对象转json字符串，默认不执行进行日期转换
	 *
	 * @param obj 对象
	 * @return 返回相应对象
	 */
	public static String toJsonString(Object obj) {

		return objectToJson(obj, false);
	}

	/**
	 * 对象转json字符串，使用默认日期转换
	 *
	 * @param obj           对象
	 * @param useDateFormat 自定义时间格式
	 * @return 返回相应对象
	 */
	public static String objectToJson(Object obj, boolean useDateFormat) {

		return objectToJson(obj, useDateFormat, DEFAULT_DATE_FORMAT);
	}

	/**
	 * 自定义日期格式
	 *
	 * @param obj        obj
	 * @param dateFormat dateFormat
	 * @return 返回相应对象
	 */
	public static String objectToJson(Object obj, String dateFormat) {

		return objectToJson(obj, true, dateFormat);

	}

	/**
	 * 对象转字符串，总处理方法，不对外开放
	 *
	 * @param obj           javabean对象
	 * @param useDateFormat useDateFormat
	 * @param dateFormat    dateFormat
	 * @return 返回相应对象
	 */
	private static String objectToJson(Object obj, boolean useDateFormat, String dateFormat) {
		if (useDateFormat) {
			return JSON.toJSONStringWithDateFormat(obj, dateFormat);
		}
		return JSON.toJSONString(obj);

	}

	/**
	 * json格式解析为List集合，不解决格式时间问题
	 *
	 * @param <T>   泛型标记
	 * @param str   json字符串
	 * @param clazz 要转换的对象
	 * @return 返回相应对象
	 */
	public static <T> List<T> jsonTolist(String str, Class<T> clazz) {

		return JSON.parseArray(str, clazz);
	}
}
