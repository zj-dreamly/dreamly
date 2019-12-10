package com.github.zj.dreamly.delayqueue.boorstrap;

import com.github.zj.dreamly.delayqueue.WheelQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <h2>QueueConfig</h2>
 *
 * @author: 苍海之南
 * @since: 0.0.1
 **/
@Slf4j
@Component
@AllArgsConstructor
public class QueueConfig {

	@Bean
	public WheelQueue wheelQueue() {
		log.info("the delay queue is starting init");
		final QueueBootstrap queueBootstrap = QueueBootstrap.getInstance();
		return queueBootstrap.start();
	}
}
