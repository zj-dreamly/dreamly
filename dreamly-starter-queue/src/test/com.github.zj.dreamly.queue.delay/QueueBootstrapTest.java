import com.github.zj.dreamly.queue.AbstractTask;
import com.github.zj.dreamly.queue.WheelQueue;
import com.github.zj.dreamly.queue.boorstrap.QueueBootstrap;

import java.util.UUID;

/**
 * <h2>QueueBootstrapTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-12-10 16:01
 **/
public class QueueBootstrapTest {

	public static void main(String[] args) {
		QueueBootstrap bootstrap = QueueBootstrap.getInstance();
		final WheelQueue queue = bootstrap.start();

		queue.add(new AbstractTask(UUID.randomUUID().toString()) {
			@Override
			public void run() {
				System.out.println("the task is running");
			}
		}, 1);

		queue.add(new AbstractTask(UUID.randomUUID().toString()) {
			@Override
			public void run() {
				System.out.println("the task is running");
			}
		}, 5);

		queue.add(new AbstractTask(UUID.randomUUID().toString()) {
			@Override
			public void run() {
				System.out.println("the task is running");
			}
		}, 20);

		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
