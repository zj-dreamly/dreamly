package com.github.zj.dreamly.tool.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 森林节点类
 *
 * @author 苍海之南
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("all")
public class ForestNode<T> extends BaseNode {

	/**
	 * 节点对象
	 */
	private T content;

	public ForestNode(Long id, Long parentId, T content) {
		this.id = id;
		this.parentId = parentId;
		this.content = content;
	}

}
