package com.mingrn.keeper.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 请求被路由之前过滤器
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 15/11/2018 20:55
 */
@Component
public class PreFilter extends ZuulFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PreFilter.class);

	/**
	 * 服务路由地址正则
	 */
	private static final String SERVICE_TOKEN_CHECKED = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\:(\\d){1,5}\\/gateway\\/api\\/(system|other..)";

	/**
	 * 对特定服务开启路由验证
	 */
	private static final Pattern SERVICE_TOKEN_PATTERN_ = Pattern.compile(SERVICE_TOKEN_CHECKED);

	/**
	 * 过滤器执行类型
	 * {@link FilterConstants#PRE_TYPE}
	 */
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	/**
	 * {@link org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter}
	 * 过滤器是 PRE 阶段最后被执行过滤器,执行顺序是 {@link FilterConstants#PRE_DECORATION_FILTER_ORDER}.
	 * 因此,在自定义前置路由时在该过滤器之后执行最为适宜
	 */
	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
	}

	/**
	 * 判断该过滤器是否执行,而不是是否进行路由
	 * 路由是在 {@link PreFilter#run()} 函数中通过
	 * <code>context.setSendZuulResponse(true);</code> 进行设置
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();

		LOGGER.info("this is a pre filter: send {} request to {}", request.getMethod(), request.getRequestURL().toString());
		// http://192.168.1.8:8001/gateway/api/system/sysGatewayController/findList
		String requestURL = request.getRequestURL().toString();
		if (SERVICE_TOKEN_PATTERN_.matcher(requestURL).find()) {
			// TODO: 17/11/2018 validation token ...
		}
		int random = new Random().nextInt(10);

		context.setSendZuulResponse(true);
//		doSomething();
		return null;

		/*context.set("isSuccess", random > 5);
		context.setSendZuulResponse(random > 5);


		// 模仿权限过滤
		if (random > 5) {
			return null;
		} else {
			throw new NotLoginException();
		}*/
	}

	private void doSomething(){
		throw new RuntimeException("------------------");
	}
}
