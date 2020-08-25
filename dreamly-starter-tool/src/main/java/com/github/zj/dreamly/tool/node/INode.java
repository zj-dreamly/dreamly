package com.github.zj.dreamly.tool.node;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chill
 */
public interface INode extends Serializable {

	/**
	 * 主键
	 *
	 * @return Integer
	 */
	Long getId();

	/**
	 * 父主键
	 *
	 * @return Integer
	 */
	Long getParentId();

	/**
	 * 子孙节点
	 *
	 * @return List
	 */
	List<INode> getChildren();

	/**
	 * 是否有子孙节点
	 *
	 * @return Boolean
	 */
	default boolean getHasChildren() {
		return false;
	}

}
