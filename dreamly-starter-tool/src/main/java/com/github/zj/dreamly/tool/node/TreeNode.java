package com.github.zj.dreamly.tool.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 树型节点类
 *
 * @author Chill
 * @since 0.0.1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeNode extends BaseNode {

	private String title;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long key;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long value;

}
