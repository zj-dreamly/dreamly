package com.github.zj.dreamly.delayqueue.simplecase.boorstrap;

import com.github.zj.dreamly.delayqueue.simplecase.WheelQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <h2>QueueConfig</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-16 11:15
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
