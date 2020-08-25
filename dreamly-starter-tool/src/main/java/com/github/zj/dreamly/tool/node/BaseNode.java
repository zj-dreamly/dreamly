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
 * @author Chill
 */
@Data
public class BaseNode implements INode {

	private static final long serialVersionUID = 1L;

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
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	protected List<INode> children = new ArrayList<>();

	/**
	 * 是否有子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private boolean hasChildren = this.getHasChildren();

	/**
	 * 是否有子孙节点
	 */
	@Override
	public boolean getHasChildren() {
		if (children.size() > 0) {
			return true;
		} else {
			return this.hasChildren;
		}
	}
}
