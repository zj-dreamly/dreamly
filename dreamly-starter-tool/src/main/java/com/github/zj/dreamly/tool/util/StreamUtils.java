package com.github.zj.dreamly.tool.util;

import cn.hutool.core.collection.CollectionUtil;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @author 苍海之南
 * @since 0.0.3
 */
public class StreamUtils {

	/**
	 * 简化map操作
	 *
	 * @param data    要操作的数据
	 * @param mapFunc function
	 * @param <T>     泛型标记
	 * @param <R>     泛型标记
	 * @return 返回的数据
	 */
	public static <T, R> List<R> map(Collection<T> data, Function<T, R> mapFunc) {
		return data.stream().map(mapFunc).collect(Collectors.toList());
	}

	/**
	 * 简化filter操作
	 *
	 * @param data       要操作的数据
	 * @param filterFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> List<T> filter(Collection<T> data, Predicate<? super T> filterFunc) {
		return data.stream().filter(filterFunc).collect(Collectors.toList());
	}

	/**
	 * 简化filter操作
	 *
	 * @param data    要操作的数据
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> List<T> distinct(Collection<T> data) {
		return data.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 求最大值
	 *
	 * @param data         要操作的数据
	 * @param func         function
	 * @param <T>泛型标记
	 * @param defaultValue 默认值
	 * @return 返回的数据
	 */
	public static <T> int maxInt(Collection<T> data, Function<T, Integer> func, int defaultValue) {
		return data.stream().map(func).max(Integer::compareTo).orElse(defaultValue);
	}

	/**
	 * 求最大值
	 *
	 * @param data    要操作的数据
	 * @param intFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> int maxInt(Collection<T> data, ToIntFunction<? super T> intFunc) {
		return data.stream().mapToInt(intFunc).max().orElse(0);
	}

	/**
	 * 求最小值
	 *
	 * @param data         要操作的数据
	 * @param intFunc      function
	 * @param <T>泛型标记
	 * @param defaultValue 默认值
	 * @return 返回的数据
	 */
	public static <T> int minInt(Collection<T> data, Function<T, Integer> intFunc, Integer defaultValue) {
		return data.stream().map(intFunc).min(Integer::compareTo).orElse(defaultValue);
	}

	/**
	 * 求最小值
	 *
	 * @param data    要操作的数据
	 * @param intFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> int minInt(Collection<T> data, ToIntFunction<? super T> intFunc) {
		return data.stream().mapToInt(intFunc).min().orElse(0);
	}

	/**
	 * 求平均数
	 *
	 * @param data    要操作的数据
	 * @param intFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T, R> double average(Collection<T> data, ToIntFunction<? super T> intFunc) {
		return data.stream().collect(Collectors.averagingInt(intFunc));
	}

	/**
	 * 求平均数
	 *
	 * @param data         要操作的数据
	 * @param mapFunc      function
	 * @param <T>泛型标记
	 * @param defaultValue 默认值
	 * @return 返回的数据
	 */
	public static <T> double average(Collection<T> data, ToIntFunction<? super T> mapFunc, int defaultValue) {
		return data.stream().mapToInt(mapFunc).average().orElse(defaultValue);
	}

	/**
	 * 求和
	 *
	 * @param data    要操作的数据
	 * @param mapFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> int sumInt(Collection<T> data, ToIntFunction<? super T> mapFunc) {
		return data.stream().mapToInt(mapFunc).sum();
	}

	/**
	 * 求和
	 *
	 * @param data    要操作的数据
	 * @param mapFunc function
	 * @param start   起始值
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> int sumInt(Collection<T> data, Function<T, Integer> mapFunc, int start) {
		return data.stream().map(mapFunc).reduce(start, Integer::sum);
	}

	/**
	 * 获取中位数
	 *
	 * @param data 要操作的数据
	 * @return 返回的数据
	 */
	public static Long median(List<Long> data) {
		Long median;
		int separator = 2;
		if (data.size() % separator == 0) {
			median = (data.get(data.size() / 2 - 1) + data.get(data.size() / 2)) / 2;
		} else {
			median = data.get(data.size() / 2);
		}
		return median;
	}

	/**
	 * 正序
	 *
	 * @param data    要操作的数据
	 * @param intFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> List<T> sortInt(Collection<T> data, ToIntFunction<? super T> intFunc) {
		return data.stream().sorted(Comparator.comparingInt(intFunc)).collect(Collectors.toList());
	}

	/**
	 * 倒序
	 *
	 * @param data    要操作的数据
	 * @param intFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> List<T> reverseInt(Collection<T> data, ToIntFunction<? super T> intFunc) {
		return CollectionUtil.reverse(sortInt(data, intFunc));
	}

	/**
	 * 统计
	 *
	 * @param data    要操作的数据
	 * @param intFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> IntSummaryStatistics summaryStatistics(Collection<T> data, ToIntFunction<? super T> intFunc) {
		return data.stream().mapToInt(intFunc).summaryStatistics();
	}

	/**
	 * 根据设置的条件返回符合数据个数
	 *
	 * @param data       要操作的数据
	 * @param filterFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> long count(Collection<T> data, Predicate<? super T> filterFunc) {
		return data.stream().filter(filterFunc).count();
	}

	/**
	 * 根据设置的最大值条件返回该条数据（返回对象为Optional，需自行判空）
	 *
	 * @param data    要操作的数据
	 * @param mapFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> Optional<T> maxData(Collection<T> data, Function<T, Integer> mapFunc) {
		return data.stream().max(Comparator.comparing(mapFunc));
	}

	/**
	 * 根据filter条件，返回map.size(2)
	 *
	 * @param data       要操作的数据
	 * @param filterFunc function
	 * @param <T>泛型标记
	 * @return 返回的数据
	 */
	public static <T> Map<Boolean, List<T>> partitioningBy(Collection<T> data, Predicate<? super T> filterFunc) {
		return data.stream().collect(Collectors.partitioningBy(filterFunc));
	}

	/**
	 * 分组
	 *
	 * @param data    要操作的数据
	 * @param mapFunc function
	 * @param <T>     泛型标记 入参类型
	 * @param <R>     泛型标记 返回参数类型
	 * @return 返回的数据
	 */
	public static <T, R> Map<R, List<T>> group(Collection<T> data, Function<T, R> mapFunc) {
		return data.stream().collect(Collectors.groupingBy(mapFunc));
	}

	/**
	 * 根据条件去重
	 *
	 * @param data         要操作的数据
	 * @param keyExtractor function
	 * @param <T>          泛型标记 入参类型
	 * @return 返回的数据
	 */
	public static <T> List<T> distinct(Collection<T> data, Function<? super T, Object> keyExtractor) {
		return data.stream().filter(distinctByKey(keyExtractor)).collect(Collectors.toList());
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new HashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}

