package com.github.zj.dreamly.queue.boorstrap;

import com.github.zj.dreamly.queue.WheelQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <h2>QueueConfig</h2>
 *
 * @author: hongjian.liu
 * @since: 0.0.1
 **/
@Slf4j
@Component
@AllArgsConstructor
public class QueueConfig {

	@Bean
	public WheelQueue wheelQueue() {
		log.info("the queue queue is starting init");
		final QueueBootstrap queueBootstrap = QueueBootstrap.getInstance();
		return queueBootstrap.start();
	}
}
