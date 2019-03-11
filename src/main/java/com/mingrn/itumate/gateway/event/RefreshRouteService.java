package com.mingrn.itumate.gateway.event;

import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 路由刷新事件
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 14/10/2018 17:53
 */
@Service
public class RefreshRouteService {

	@Resource
	private RouteLocator routeLocator;

	@Resource
	private ApplicationEventPublisher publisher;

	/**
	 * 刷新路由
	 */
	public void refreshRoute() {
		RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
		publisher.publishEvent(routesRefreshedEvent);
	}

}