package com.github.zj.dreamly.tool.node;

import cn.hutool.json.JSONUtil;
import com.github.zj.dreamly.tool.api.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 苍海之南
 * @since 2019年5月20日
 */
public class NodeTest {

	public static void main(String[] args) {
		List<ForestNode> list = new ArrayList<>();
		list.add(new ForestNode<>(1L, 0L, new ResponseEntity<>()));
		list.add(new ForestNode<>(2L, 0L, new ResponseEntity<>()));
		list.add(new ForestNode<>(3L, 1L, new ResponseEntity<>()));
		list.add(new ForestNode<>(4L, 2L, new ResponseEntity<>()));
		list.add(new ForestNode<>(5L, 3L, new ResponseEntity<>()));
		list.add(new ForestNode<>(6L, 4L, new ResponseEntity<>()));
		list.add(new ForestNode<>(7L, 3L, new ResponseEntity<>()));
		list.add(new ForestNode<>(8L, 5L, new ResponseEntity<>()));
		list.add(new ForestNode<>(9L, 6L, new ResponseEntity<>()));
		list.add(new ForestNode<>(10L, 9L, new ResponseEntity<>()));
		List<ForestNode> tns = ForestNodeMerger.merge(list);
		tns.forEach(node ->
			System.out.println(JSONUtil.toJsonStr(node))
		);
	}

}
