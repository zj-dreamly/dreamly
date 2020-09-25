package com.github.zj.dreamly.queue.boorstrap;

import com.github.zj.dreamly.queue.AbstractTask;
import com.github.zj.dreamly.queue.Slot;
import com.github.zj.dreamly.queue.WheelQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h2>类似钟表的秒针，队列是表盘，这里有个类似秒针的循环器
 * 每秒循环一次；就类似秒针再走。
 * </h2>
 *
 * @author: hongjian.liu
 * @since: 0.0.1
 **/
@Slf4j
public class QueueScanTimer extends TimerTask {
	/**
	 * 环形队列
	 */
	private WheelQueue queue;

	private static ThreadFactory slotThreadFactory =
		new MyDefaultThreadFactory("slotThreadGroup");

	private static ThreadFactory taskThreadFactory =
		new MyDefaultThreadFactory("taskThreadGroup");

	/**
	 * 处理每个槽位的线程，循环到这个槽位，立即丢到一个线程去处理，然后继续循环队列。
	 */
	private ThreadPoolExecutor slotPool = new ThreadPoolExecutor(10, Integer.MAX_VALUE,
		0L, TimeUnit.MILLISECONDS,
		new LinkedBlockingQueue<>(), slotThreadFactory);

	/**
	 * 处理每一个槽位中task集合的线程， 集合中的每个任务一个线程
	 */
	private ThreadPoolExecutor taskPool = new ThreadPoolExecutor(10, Integer.MAX_VALUE,
		0L, TimeUnit.MILLISECONDS,
		new LinkedBlockingQueue<>(), taskThreadFactory);

	public QueueScanTimer(WheelQueue queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {

		try {
			if (queue == null) {
				return;
			}

			Calendar calendar = Calendar.getInstance();
			int currentSecond = calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
			Slot slot = queue.peek(currentSecond);
			log.debug("current solt:" + currentSecond);

			slotPool.execute(new SlotTask(slot.getTasks(), currentSecond));
		} catch (Exception e) {
			//这里一个槽位的屏蔽异常，继续执行。
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 槽位任务
	 *
	 * @author hongjian.liu
	 */
	final class SlotTask implements Runnable {
		ConcurrentLinkedQueue<AbstractTask> tasks;
		int currentSecond;

		private SlotTask(ConcurrentLinkedQueue<AbstractTask> tasks, int currentSecond) {
			super();
			this.tasks = tasks;
			this.currentSecond = currentSecond;
		}

		@Override
		public void run() {
			if (tasks == null) {
				return;
			}
			String taskId;
			Iterator<AbstractTask> it = tasks.iterator();
			while (it.hasNext()) {
				AbstractTask task = it.next();
				if (log.isDebugEnabled()) {
					log.debug("running_current_solt:currentSecond={}, task={}, taskQueueSize={}",
						currentSecond, task.toString(), tasks.size());
				}
				taskId = task.getId();
				if (task.getCycleNum() <= 0) {
					taskPool.execute(task);
					it.remove();
					queue.getTaskSlotMapping().remove(taskId);
				} else {
					if (log.isDebugEnabled()) {
						log.debug("countDown#running_current_solt:currentSecond={}, task={}",
							currentSecond, task.toString());
					}
					task.countDown();
				}
			}
		}
	}

	/**
	 * The default thread factory
	 */
	private static class MyDefaultThreadFactory implements ThreadFactory {
		private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

		private final ThreadGroup group;

		private final AtomicInteger threadNumber = new AtomicInteger(1);

		private final String namePrefix;

		MyDefaultThreadFactory(String groupName) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() :
				Thread.currentThread().getThreadGroup();
			namePrefix = groupName + "-pool-" +
				POOL_NUMBER.getAndIncrement() +
				"-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r,
				namePrefix + threadNumber.getAndIncrement(),
				0);
			if (t.isDaemon()) {
				t.setDaemon(false);
			}
			if (t.getPriority() != Thread.NORM_PRIORITY) {
				t.setPriority(Thread.NORM_PRIORITY);
			}
			return t;
		}
	}
}
