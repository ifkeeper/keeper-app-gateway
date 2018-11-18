package com.mingrn.keeper.gateway.config;

import com.mingrn.keeper.gateway.zuul.CustomDynamicRouteLocator;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;

/**
 * 自定义动态路由配置
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 14/10/2018 17:49
 */
@Configuration
public class CustomDynamicRouteConfig {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private ZuulProperties zuulProperties;

	@Resource
	private ServerProperties serverProperties;

	@Bean
	public CustomDynamicRouteLocator routeLocator() {
		CustomDynamicRouteLocator routeLocator = new CustomDynamicRouteLocator(this.serverProperties.getServlet().getServletPrefix(), this.zuulProperties);
		routeLocator.setJdbcTemplate(jdbcTemplate);
		return routeLocator;
	}

}