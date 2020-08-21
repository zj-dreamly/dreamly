package com.github.zj.dreamly.tool.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 苍海之南
 */
public abstract class BaseEntityWrapper<E, V> {

    /**
     * 单个实体类包装
     *
     * @param entity 实体类
     * @return V
     */
    public abstract V entityVo(E entity);

    /**
     * 实体类集合包装
     *
     * @param list 列表
     * @return List V
     */
    public List<V> listVo(List<E> list) {
        return list.stream().map(this::entityVo).collect(Collectors.toList());
    }

    /**
     * 分页实体类集合包装
     *
     * @param pages 分页
     * @return Page V
     */
    public IPage<V> pageVo(IPage<E> pages) {
        List<V> records = listVo(pages.getRecords());
        IPage<V> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);
        return pageVo;
    }
}
