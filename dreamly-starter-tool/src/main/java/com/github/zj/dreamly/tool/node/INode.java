package com.github.zj.dreamly.tool.node;

import java.util.List;

/**
 * @author Chill
 */
public interface INode {

	/**
	 * 主键
	 *
	 * @return id
	 */
	Long getId();

	/**
	 * 父主键
	 *
	 * @return parentId
	 */
	Long getParentId();

	/**
	 * 子孙节点
	 *
	 * @return children
	 */
	List<INode> getChildren();

}
