package com.fxb.world.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {

	// 精确到 cluster 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.fxb.world.dao.cluster";
	static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

/*	@Value("${cluster.datasource.url}")
	private String url;

	@Value("${cluster.datasource.username}")
	private String user;

	@Value("${cluster.datasource.password}")
	private String password;

	@Value("${cluster.datasource.driverClassName}")
	private String driverClass;

	@Bean(name = "clusterDataSource")
	public DataSource clusterDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}*/
	@Autowired
	@Qualifier("clusterDataSource")
	private DataSource clusterDataSource;
	
	@Bean(name = "clusterTransactionManager")
	public DataSourceTransactionManager clusterTransactionManager() {
		return new DataSourceTransactionManager(clusterDataSource);
	}

	@Bean(name = "clusterSqlSessionFactory")
	public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(clusterDataSource);
		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}
}
