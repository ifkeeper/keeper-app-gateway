package com.mingrn.keeper.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 请求被路由之前过滤器
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 15/11/2018 20:55
 */
@Component
public class PostFilter extends ZuulFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostFilter.class);

	/**
	 * 过滤器执行类型
	 * {@link FilterConstants#POST_TYPE}
	 */
	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}

	/**
	 * {@link org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter}
	 * 过滤器是 POST 阶段第一个被执行过滤器,执行顺序是 {@link FilterConstants#SEND_ERROR_FILTER_ORDER}.
	 */
	@Override
	public int filterOrder() {
		return FilterConstants.SEND_ERROR_FILTER_ORDER + 1;
	}

	/**
	 * 判断该过滤器是否执行
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		return null;
	}
}
