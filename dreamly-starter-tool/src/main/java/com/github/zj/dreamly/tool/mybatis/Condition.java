package com.github.zj.dreamly.tool.mybatis;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * 分页工具
 *
 * @author Chill
 */
public class Condition {

	/**
	 * 转化成mybatis plus中的Page
	 *
	 * @param query 查询条件
	 * @return IPage
	 */
	public static <T> IPage<T> getPage(Query query) {
		Page<T> page = new Page<>(query.getCurrent(), query.getSize(), 10);
		page.setAsc(StrUtil.split(SqlKeyword.filter(query.getAscs()), StrUtil.COMMA));
		page.setDesc(StrUtil.split(SqlKeyword.filter(query.getDescs()), StrUtil.COMMA));
		return page;
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param entity 实体
	 * @param <T>    类型
	 * @return QueryWrapper
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
		return new QueryWrapper<>(entity);
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param query 查询条件
	 * @param clazz 实体类
	 * @param <T>   类型
	 * @return QueryWrapper
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Class<T> clazz) {
		Dict exclude = Dict.create()
			.set("current", "current")
			.set("size", "size")
			.set("ascs", "ascs")
			.set("descs", "descs");
		return getQueryWrapper(query, exclude, clazz);
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param query   查询条件
	 * @param exclude 排除的查询条件
	 * @param clazz   实体类
	 * @param <T>     类型
	 * @return QueryWrapper
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query,
													  Map<String, Object> exclude, Class<T> clazz) {
		exclude.forEach((k, v) -> query.remove(k));
		QueryWrapper<T> qw = new QueryWrapper<>();
		qw.setEntity(BeanUtils.instantiateClass(clazz));
		SqlKeyword.buildCondition(query, qw);
		return qw;
	}

}
