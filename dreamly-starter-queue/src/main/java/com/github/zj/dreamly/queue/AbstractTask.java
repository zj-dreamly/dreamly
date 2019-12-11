package com.github.zj.dreamly.queue;

import lombok.Data;

/**
 * <h2>要运行的任务</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.1
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
