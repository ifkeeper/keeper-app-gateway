package com.mingrn.keeper.gateway.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * 系统网关实体类
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 13/11/2018 20:41
 */
@Entity
@Table(name = "sys_gateway")
public class SysGateway implements Serializable {
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	/**
	 * 网关应用名称
	 */
	@Column(name = "app_name")
	private String appName;

	/**
	 * 路由规则，如：/hello-api/**
	 */
	private String path;

	/**
	 * 服务id，如：hello-service。与 url 只能存在一个。
	 */
	@Column(name = "service_id")
	private String serviceId;

	/**
	 * 服务地址，如：http://localhost:8080。与 服务ID 只能存在一个
	 */
	private String url;

	/**
	 * 是否开启重试机制
	 */
	private Boolean retryable;

	/**
	 * 是否剥离路由前缀
	 */
	@Column(name = "strip_prefix")
	private Boolean stripPrefix;

	/**
	 * 是否开启敏感头信息
	 */
	@Column(name = "custom_sensitive_headers")
	private Boolean customSensitiveHeaders;

	/**
	 * 是否立即启用路由
	 */
	@Column(name = "apply_enable")
	private Boolean applyEnable;

	/**
	 * 状态。1：启用，2：禁用
	 */
	private Integer status;

	/**
	 * 是否已删除。1：正常，2：删除
	 */
	private Integer deleted;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 说明
	 */
	private String description;

	private static final long serialVersionUID = 1L;

	/**
	 * 获取主键
	 *
	 * @return id - 主键
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置主键
	 *
	 * @param id 主键
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取网关应用名称
	 *
	 * @return app_name - 网关应用名称
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * 设置网关应用名称
	 *
	 * @param appName 网关应用名称
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * 获取路由规则，如：/hello-api/**
	 *
	 * @return path - 路由规则，如：/hello-api/**
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置路由规则，如：/hello-api/**
	 *
	 * @param path 路由规则，如：/hello-api/**
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 获取服务id，如：hello-service。与 url 只能存在一个。
	 *
	 * @return service_id - 服务id，如：hello-service。与 url 只能存在一个。
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * 设置服务id，如：hello-service。与 url 只能存在一个。
	 *
	 * @param serviceId 服务id，如：hello-service。与 url 只能存在一个。
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * 获取服务地址，如：http://localhost:8080。与 服务ID 只能存在一个
	 *
	 * @return url - 服务地址，如：http://localhost:8080。与 服务ID 只能存在一个
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置服务地址，如：http://localhost:8080。与 服务ID 只能存在一个
	 *
	 * @param url 服务地址，如：http://localhost:8080。与 服务ID 只能存在一个
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取是否开启重试机制
	 *
	 * @return retryable - 是否开启重试机制
	 */
	public Boolean getRetryable() {
		return retryable;
	}

	/**
	 * 设置是否开启重试机制
	 *
	 * @param retryable 是否开启重试机制
	 */
	public void setRetryable(Boolean retryable) {
		this.retryable = retryable;
	}

	/**
	 * 获取是否剥离路由前缀
	 *
	 * @return strip_prefix - 是否剥离路由前缀
	 */
	public Boolean getStripPrefix() {
		return stripPrefix;
	}

	/**
	 * 设置是否剥离路由前缀
	 *
	 * @param stripPrefix 是否剥离路由前缀
	 */
	public void setStripPrefix(Boolean stripPrefix) {
		this.stripPrefix = stripPrefix;
	}

	/**
	 * 获取是否开启敏感头信息
	 *
	 * @return custom_sensitive_headers - 是否开启敏感头信息
	 */
	public Boolean getCustomSensitiveHeaders() {
		return customSensitiveHeaders;
	}

	/**
	 * 设置是否开启敏感头信息
	 *
	 * @param customSensitiveHeaders 是否开启敏感头信息
	 */
	public void setCustomSensitiveHeaders(Boolean customSensitiveHeaders) {
		this.customSensitiveHeaders = customSensitiveHeaders;
	}

	/**
	 * 获取是否立即启用路由
	 *
	 * @return apply_enable - 是否立即启用路由
	 */
	public Boolean getApplyEnable() {
		return applyEnable;
	}

	/**
	 * 设置是否立即启用路由
	 *
	 * @param applyEnable 是否立即启用路由
	 */
	public void setApplyEnable(Boolean applyEnable) {
		this.applyEnable = applyEnable;
	}

	/**
	 * 获取状态。1：启用，2：禁用
	 *
	 * @return status - 状态。1：启用，2：禁用
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置状态。1：启用，2：禁用
	 *
	 * @param status 状态。1：启用，2：禁用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取是否已删除。1：正常，2：删除
	 *
	 * @return deleted - 是否已删除。1：正常，2：删除
	 */
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * 设置是否已删除。1：正常，2：删除
	 *
	 * @param deleted 是否已删除。1：正常，2：删除
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	/**
	 * 获取创建时间
	 *
	 * @return create_time - 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createTime 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取说明
	 *
	 * @return description - 说明
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置说明
	 *
	 * @param description 说明
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}