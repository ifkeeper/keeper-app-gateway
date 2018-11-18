package com.mingrn.keeper.gateway.enums;

/**
 * 请求路由类型枚举类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 15/11/2018 20:54
 */
public enum FilterTypeEnum {

	/**
	 * 请求被路由之前调用
	 */
	PRE("pre"),
	/**
	 * 请求被路由时调用
	 */
	ROUTE("ROUTE"),
	/**
	 * 在 ROUTE 和 ERROR 之后调用
	 */
	POST("POST"),
	/**
	 * 处理请求时发生错误是调用
	 */
	ERROR("ERROR");

	private String type;

	FilterTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
