package com.github.zj.dreamly.tool.util;

/**
 * 填充数据工具类
 * @author zj
 * @date 2021年9月2日 15:51:52
 */
public interface IFillData {

    /**
     * 条件判断
     *
     * @param format 入参
     * @return 出参
     */
    boolean test(String format);

    /**
     * 设置日期
     * @param format 日期
     */
    void doSetDate(String format);

}
