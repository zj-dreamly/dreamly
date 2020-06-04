package com.github.zj.dreamly.queue.util;


import com.github.zj.dreamly.queue.AbstractTask;
import com.github.zj.dreamly.queue.TaskAttribute;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * <h2>任务的一些属性设置</h2>
 *
 * @author: hongjian.liu
 * @since: 0.0.1
 **/
public class TaskAttributeUtil {
	/**
	 * setAttribute。
	 * <ul>
	 * <li>计算任务所在槽位</li>
	 * <li>记录任务的加入时间，应该几点执行</li>
	 * <li>任务Id和槽位的映射记录到taskSlotMapping中</li>
	 * </ul>
	 *
	 * @param secondsLater    以当前时间点为基准，多少秒以后执行
	 * @param task            task
	 * @param taskSlotMapping taskSlotMapping
	 * @return 返回所在槽位索引
	 */
	public static int setAttribute(int secondsLater, AbstractTask task, Map<String, TaskAttribute> taskSlotMapping) {
		TaskAttribute taskAttribute = new TaskAttribute();
		Calendar calendar = Calendar.getInstance();
		//把当前时间的分钟和秒加起来
		int currentSecond = calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
		int soltIndex = (currentSecond + secondsLater) % 3600;

		task.setCycleNum(secondsLater / 3600);

		calendar.add(Calendar.SECOND, 1);
		taskAttribute.setExecuteTime(calendar.getTime());
		taskAttribute.setSoltIndex(soltIndex);
		taskAttribute.setJoinTime(new Date());
		taskSlotMapping.put(task.getId(), taskAttribute);
		return soltIndex;
	}

}
