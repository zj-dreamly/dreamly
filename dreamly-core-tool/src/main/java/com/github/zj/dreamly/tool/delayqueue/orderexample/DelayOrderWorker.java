package com.github.zj.dreamly.tool.delayqueue.orderexample;
/**
 * 具体执行相关业务的业务类
 * @author whd
 * @date 2017年9月25日 上午12:49:32
 */
public class DelayOrderWorker  implements Runnable {
 
	@Override
	public void run() {
		//相关业务逻辑处理
		System.out.println(Thread.currentThread().getName()+" do something ……");
	}
}
