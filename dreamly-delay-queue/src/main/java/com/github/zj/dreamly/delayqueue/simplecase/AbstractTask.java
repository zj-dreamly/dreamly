package com.github.zj.dreamly.delayqueue.simplecase;

import lombok.Data;

/**
 * <h2>要运行的任务</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-16 09:11
 **/
@Data
public abstract class AbstractTask implements Runnable{
	/**
	 * 任务id. 如果是分布式部署多台应用，那次id要是全局唯一的
	 */
	private String id;
	/**
	 * 第几圈
	 */
	private Integer cycleNum;

	/**
	 * @param id 任务id
	 */
	public AbstractTask(String id) {
		super();
		this.id = id;
	}

	/**
	 * 倒计数，为0时即可执行此任务
	 */
	public void countDown() {
		this.cycleNum--;
	}
}
