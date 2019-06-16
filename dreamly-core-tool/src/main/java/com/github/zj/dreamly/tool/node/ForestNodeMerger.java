package com.github.zj.dreamly.tool.node;

import java.util.List;

/**
 * 森林节点归并类
 *
 * @author 苍海之南
 * @since 2019年5月20日
 */
@SuppressWarnings("all")
public class ForestNodeMerger {

	/**
	 * 将节点数组归并为一个森林（多棵树）（填充节点的children域）
	 * 时间复杂度为O(n^2)
	 *
	 * @param items 节点域
	 * @return 多棵树的根节点集合
	 */
	public static <T extends INode> List<T> merge(List<T> items) {
		ForestNodeManager<T> forestNodeManager = new ForestNodeManager<>(items);
		items.forEach(forestNode -> {
			if (forestNode.getParentId() != 0) {
				INode node = forestNodeManager.getTreeNodeAT(forestNode.getParentId());
				if (node != null) {
					node.getChildren().add(forestNode);
				} else {
					forestNodeManager.addParentId(forestNode.getId());
				}
			}
		});
		return forestNodeManager.getRoot();
	}

}
