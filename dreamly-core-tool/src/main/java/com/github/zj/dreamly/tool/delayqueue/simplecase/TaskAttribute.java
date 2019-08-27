package com.github.zj.dreamly.tool.delayqueue.simplecase;

import lombok.Data;

import java.util.Date;

/**
 * <h2>任务属性</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-16 09:12
 **/
@Data
public class TaskAttribute {
	/**
	 * 第几个槽位
	 */
	private int soltIndex;
	/**
	 * 任务应该什么时候执行
	 */
	private Date executeTime;
	/**
	 * 任务加入槽位的时间
	 */
	private Date joinTime;
}
