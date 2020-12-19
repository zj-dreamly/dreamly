package com.github.zj.dreamly.queue.boorstrap;

import com.github.zj.dreamly.queue.WheelQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <h2>QueueBootstrap</h2>
 *
 * @author: hongjian.liu
 * @since: 0.0.1
 **/
@Slf4j
public class QueueBootstrap {

	private static final QueueBootstrap QUEUE_BOOTSTRAP = new QueueBootstrap();

	public static QueueBootstrap getInstance() {
		return QUEUE_BOOTSTRAP;
	}

	private final ScheduledExecutorService scheduledExecutorService = new
		ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
		new BasicThreadFactory.Builder().namingPattern("queue-schedule-pool-%d").daemon(true).build());

	/**
	 * can set some profile current is no sense
	 */
	private volatile Map<String, Object> options = new ConcurrentHashMap<>();

	/**
	 * Returns the options
	 */
	public Map<String, Object> getOptions() {
		return new TreeMap<>(options);
	}

	private QueueBootstrap() {
	}

	/**
	 * Create a circular queue; and enable the scheduled scan queue
	 */
	public WheelQueue start() {
		log.info("scanning starting...");
		WheelQueue wheelQueue = WheelQueue.getInstance();
		// 定义任务
		QueueScanTimer timerTask = new QueueScanTimer(wheelQueue);
		// 设置任务的执行，1秒后开始，每1秒执行一次
		scheduledExecutorService.scheduleWithFixedDelay(timerTask, 1, 1, TimeUnit.SECONDS);
		log.info("scanning start up.");

		return wheelQueue;
	}

	/**
	 * 停止此队列运行。
	 */
	public void shutdown() {
		// 只停止扫描队列。已运行的任务暂不停止。
		scheduledExecutorService.shutdown();
	}

	/**
	 * Sets the options
	 */
	public void setOptions(Map<String, Object> options) {
		if (options == null) {
			throw new NullPointerException("options");
		}
		this.options = new HashMap<>(options);
	}

	/**
	 * Returns the value of the option with the specified key.
	 *
	 * @param key the option name
	 * @return the option value if the option is found. {@code null} otherwise.
	 */
	public Object getOption(String key) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		return options.get(key);
	}

	/**
	 * Sets an option with the specified key and value. If there's already an
	 * option with the same key, it is replaced with the new value. If the
	 * specified value is {@code null}, an existing option with the specified
	 * key is removed.
	 *
	 * @param key   the option name
	 * @param value the option value
	 */
	public void setOption(String key, Object value) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		if (value == null) {
			options.remove(key);
		} else {
			options.put(key, value);
		}
	}

	/**
	 * 设置一个值
	 *
	 * @param option key
	 * @param value  value
	 * @return QueueBootstrap
	 */
	public QueueBootstrap option(String option, String value) {
		if (option == null) {
			throw new NullPointerException("option");
		}
		if (value == null) {
			options.remove(option);
		} else {
			options.put(option, value);
		}
		return this;
	}
}
