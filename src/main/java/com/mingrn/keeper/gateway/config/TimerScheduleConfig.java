package com.mingrn.keeper.gateway.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * 所有的定时任务都放在一个线程池中,定时任务启动时使用不同都线程
 * </p>
 * <a href="https://my.oschina.net/jack90john/blog/1506474">参考</a>
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 15/10/2018 09:40
 */
@Configuration
public class TimerScheduleConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
				new BasicThreadFactory.Builder().namingPattern("timer-schedule-thread-poor-%d").daemon(true).build());

		//设定一个长度10的定时任务线程池
		taskRegistrar.setScheduler(executorService);
	}
}