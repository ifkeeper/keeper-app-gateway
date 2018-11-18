package com.mingrn.keeper.gateway.timer;

import com.mingrn.keeper.gateway.event.RefreshRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时刷新路由
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 16/11/2018 09:33
 */
@Component
public class ScheduledTasks {

	@Resource
	private RefreshRouteService refreshRouteService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

	/**
	 * cron接受cron表达式,根据cron表达式确定定时规则
	 * </p>
	 * <code>@Scheduled(fixedDelay = 5000)</code>
	 * 表示当前方法执行完毕5000ms后,Spring scheduling会再次调用该方法
	 * </p>
	 * <code>@Scheduled(fixedRate = 5000)</code>
	 * 表示当前方法开始执行5000ms后,Spring scheduling会再次调用该方法
	 * </p>
	 * <code>@Scheduled(initialDelay = 1000, fixedRate = 5000)</code>
	 * 表示延迟1000ms执行第一次任务
	 */
	@Scheduled(cron = "0 0/2 * * * ? *")
	public void refreshRouteByCron() {
		refreshRouteService.refreshRoute();
		LOGGER.info("开始执行路由刷新事件 >>>>");
	}

}