package com.github.zj.dreamly.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <h2>槽位</h2>
 *
 * @author: hongjian.liu
 * @since: 0.0.1
 **/
public class Slot {
	/**
	 * 用来存放任务。也可更加时间情况使用其他容器存放，如：ConcurrentSkipListSet,Set, Map
	 */
	private final ConcurrentLinkedQueue<AbstractTask> tasks = new ConcurrentLinkedQueue<>();

	public ConcurrentLinkedQueue<AbstractTask> getTasks() {
		return tasks;
	}

	/**
	 * 加入一个任务
	 *
	 * @param task 任务
	 */
	protected void add(AbstractTask task) {
		tasks.add(task);
	}

	/**
	 * 删除一个任务
	 *
	 * @param taskId 任务id
	 */
	protected void remove(String taskId) {
		tasks.removeIf(abstractTask -> taskId.equals(abstractTask.getId()));
	}
}
