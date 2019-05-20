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
public class ForestNode extends BaseNode {

	/**
	 * 节点内容
	 */
	private Object content;

	public ForestNode(Long id, Long parentId, Object content) {
		this.id = id;
		this.parentId = parentId;
		this.content = content;
	}

}
