package com.mingrn.keeper.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableZuulProxy
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "com.mingrn.keeper")
public class Application {

	/**
	 * 将路由规则进行模式转换,比如服务实例为 userService-v1 则被映射的路径为 v1/userService
	 */
	/*@Bean
	public PatternServiceRouteMapper patternServiceRouteMapper() {
		return new PatternServiceRouteMapper("(?<name>.*)-(?<version>v.*$)", "${version}/${name}");
	}*/

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
	}
}