package com.mingrn.keeper.gateway.zuul;

import com.mingrn.keeper.gateway.domain.SysGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义动态路由
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 14/10/2018 17:45
 */
public class CustomDynamicRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

	private JdbcTemplate jdbcTemplate;

	private ZuulProperties properties;

	private final static Logger LOGGER = LoggerFactory.getLogger(CustomDynamicRouteLocator.class);

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public CustomDynamicRouteLocator(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
		this.properties = properties;
		LOGGER.info("servletPath:{}", servletPath);
	}

	/*父类已经提供了这个方法，这里写出来只是为了说明这一个方法很重要！！！
    @Override
    protected void doRefresh() {
        super.doRefresh();
    }*/


	@Override
	public void refresh() {
		doRefresh();
	}

	@Override
	protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
		LOGGER.info("Start loading routing information >>>>");
		LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>(8);
		//从application.properties中加载路由信息
		routesMap.putAll(super.locateRoutes());
		//从db中加载路由信息
		routesMap.putAll(locateRoutesFromDB());
		LOGGER.info("Is the refresh routing >>>>");
		//优化一下配置
		LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
		for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
			String path = entry.getKey();
			// Prepend with slash if not already present.
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (StringUtils.hasText(this.properties.getPrefix())) {
				path = this.properties.getPrefix() + path;
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
			}
			values.put(path, entry.getValue());
		}
		return values;
	}

	/**
	 * 从数据库读取路由信息
	 */
	private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDB() {
		Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
		List<SysGateway> results = jdbcTemplate.query("SELECT * FROM `sys_gateway` WHERE `status` = 1 AND `deleted` = 1", new BeanPropertyRowMapper<>(SysGateway.class));
		for (SysGateway result : results) {
			if (org.apache.commons.lang3.StringUtils.isBlank(result.getPath()) || org.apache.commons.lang3.StringUtils.isBlank(result.getServiceId())) {
				continue;
			}

			ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
			try {
				org.springframework.beans.BeanUtils.copyProperties(result, zuulRoute);
			} catch (Exception e) {
				LOGGER.error("=============load zuul route info from db with error==============", e);
			}
			routes.put(zuulRoute.getPath(), zuulRoute);
			LOGGER.info("Routing information: {}", zuulRoute.toString());
		}
		return routes;
	}
}