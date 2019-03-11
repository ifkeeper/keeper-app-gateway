package com.mingrn.itumate.gateway.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * 主数据源配置
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 13/11/2018 21:04
 */
@Configuration
public class MyBatisConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisConfig.class);

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.driver-class-name}")
	private String dbDriverClassName;

	@Value("${spring.datasource.username}")
	private String dbName;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Value("${spring.datasource.filters}")
	private String dbFilters;

	/**
	 * 数据源
	 */
	@Primary
	@Bean(name = "dataSourceDruid")
	@ConfigurationProperties("spring.datasource")
	public DruidDataSource druidDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(dbUrl);
		dataSource.setDriverClassName(dbDriverClassName);
		dataSource.setUsername(dbName);
		dataSource.setPassword(dbPassword);

		try {
			dataSource.setFilters(dbFilters);
		} catch (SQLException e) {
			LOGGER.error("druid configuration initialization filter", e);
		}


		// 使用 UTF8mb4 编码集
		String connectionInitSql = "SET NAMES utf8mb4";
		StringTokenizer tokenizer = new StringTokenizer(connectionInitSql, ";");
		dataSource.setConnectionInitSqls(Collections.list(tokenizer));

		return dataSource;
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
		sqlSessionFactoryBean.setTypeAliasesPackage("com.mingrn.itumate.**.domain");

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
		mapperScannerConfigurer.setBasePackage("com.mingrn.itumate.**.mapper");

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
		 * com.mingrn.itumate.core.Mapper = 通用Mapper包路径位置
		 */
		properties.setProperty("`", "com.mingrn.itumate.core.Mapper");
		properties.setProperty("mappers", "com.mingrn.itumate.core.Mapper");
		// insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
		properties.setProperty("notEmpty", "false");
		properties.setProperty("ORDER", "BEFORE");
		properties.setProperty("IDENTITY", "SELECT REPLACE(UUID(),\"-\",\"\")");
		mapperScannerConfigurer.setProperties(properties);

		return mapperScannerConfigurer;
	}
}
