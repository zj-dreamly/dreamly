package com.github.zj.dreamly.tool.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author zj
 * @date 2021年9月2日 14:31:44
 */
public class FillDataUtil {

	public static List<IFillData> fill(List<IFillData> list, IFillData defaultData,
									   Date beginTime, Date endTime) {

		List<IFillData> newList = new ArrayList<>();

		long between = DateUtil.between(beginTime, endTime, DateUnit.DAY);
		for (int i = 0; i <= between; i++) {

			String format = DateUtil.format(DateUtil.offsetDay(beginTime, i), DatePattern.NORM_DATE_FORMAT);
			defaultData.doSetDate(format);
			newList.add(doFill(list, item -> item.test(format), defaultData));
		}

		return newList;
	}

	private static <T> T doFill(List<T> list, Predicate<? super T> filterFunc, T defaultData) {
		List<T> filterList = list.stream()
			.filter(filterFunc)
			.collect(Collectors.toList());

		if (CollectionUtil.isNotEmpty(filterList)) {

			return filterList.get(0);

		} else {
			return defaultData;
		}
	}
}
