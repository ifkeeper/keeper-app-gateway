package com.mingrn.keeper.gateway.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 主数据源配置
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 13/11/2018 21:04
 */
@Configuration
public class MyBatisConfig {

	/**
	 * 数据源
	 */
	@Primary
	@Bean(name = "dataSourceDruid")
	@ConfigurationProperties("spring.datasource")
	public DruidDataSource druidDataSource() {
		return new DruidDataSource();
	}

	/**
	 * 工厂
	 */
	@Primary
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceDruid") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		// 项目domain包路径位置
		sqlSessionFactoryBean.setTypeAliasesPackage("com.mingrn.keeper.**.domain");

		// 配置分页插件，详情请查阅官方文档
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		// 分页尺寸为0时查询所有纪录不再执行分页
		properties.setProperty("pageSizeZero", "true");
		// 页码<=0 查询第一页，页码>=总页数查询最后一页
		properties.setProperty("reasonable", "true");
		// 支持通过 Mapper 接口参数来传递分页参数
		properties.setProperty("supportMethodsArguments", "true");
		pageHelper.setProperties(properties);

		sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});

		// XML
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * 事物
	 */
	@Primary
	@Bean(name = "dataSourceTransactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSourceDruid") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * 模板
	 */
	@Primary
	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	/**
	 * mapper 扫描
	 */
	@Bean
	public static MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
		// 项目Mapper包路径位置
		mapperScannerConfigurer.setBasePackage("com.mingrn.keeper.**.mapper");

		// 配置通用Mapper，详情请查阅官方文档
		Properties properties = new Properties();
		/**
		 * (Service.findByCondition --> ConditionMapper<T>)
		 * 解决MyBatis通用插件Mapper调用条件查询报如下异常：
		 * ----------------------------------------------------------------------------------------------------------
		 * org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.builder.BuilderException:
		 * Error invoking SqlProvider method (tk.mybatis.mapper.provider.ConditionProvider.dynamicSQL).
		 * Cause: java.lang.InstantiationException: tk.mybatis.mapper.provider.ConditionProvider
		 * ----------------------------------------------------------------------------------------------------------
		 * mappers = 属性名称(固定写法)
		 * com.mingrn.keeper.core.Mapper = 通用Mapper包路径位置
		 */
		properties.setProperty("`", "com.mingrn.keeper.core.Mapper");
		properties.setProperty("mappers", "com.mingrn.keeper.core.Mapper");
		// insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
		properties.setProperty("notEmpty", "false");
		properties.setProperty("ORDER", "BEFORE");
		properties.setProperty("IDENTITY", "SELECT REPLACE(UUID(),\"-\",\"\")");
		mapperScannerConfigurer.setProperties(properties);

		return mapperScannerConfigurer;
	}
}
