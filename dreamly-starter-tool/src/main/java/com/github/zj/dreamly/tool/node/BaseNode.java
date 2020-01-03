package com.github.zj.dreamly.tool.node;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点基类
 *
 * @author 苍海之南
 */
@Data
public class BaseNode implements INode {

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	protected Long id;

	/**
	 * 父节点ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	protected Long parentId;

	/**
	 * 子孙节点
	 */
	protected List<INode> children = new ArrayList<>();

}
