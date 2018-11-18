package com.mingrn.keeper.gateway.web;

import com.mingrn.keeper.gateway.event.RefreshRouteService;
import com.mingrn.keeper.global.result.ResponseMsgUtil;
import com.mingrn.keeper.global.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(description = "Gateway API")
public class ApiController {

	@Resource
	private ZuulHandlerMapping zuulHandlerMapping;

	@Resource
	private RefreshRouteService refreshRouteService;

	@GetMapping("/refreshRoute")
	@ApiOperation(value = "刷新网关", notes = "手动触发API网关刷新路由")
	public Result refreshRoute() {
		refreshRouteService.refreshRoute();
		return ResponseMsgUtil.success(null);
	}

	@ApiOperation(value = "查看路由", notes = "手动查看路由")
	@GetMapping("/watchNowRoute")
	public Result watchNowRoute() {
		//可以用debug模式看里面具体是什么
		Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
		return ResponseMsgUtil.success(handlerMap);
	}

}