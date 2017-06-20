package com.fxb.world.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.pagehelper.PageHelper;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {
	 private static Log logger = LogFactory.getLog(MasterDataSourceConfig.class);  
	  
	// 精确到 master 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.fxb.world.dao";
	static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

	  @Value("$pagehelper.offsetAsPageNum}")
	    private String offsetAsPageNum;
	 
	    @Value("${pagehelper.helperDialect}")
	    private String helperDialect;

	
	@Autowired
	@Qualifier("fxbDataSource")
	private DataSource fxbDataSource;

	 //事务管理  
	@Bean(name = "masterTransactionManager")
	@Primary
	public DataSourceTransactionManager masterTransactionManager() {
		return new DataSourceTransactionManager(fxbDataSource);
	}

	@Bean(name = "masterSqlSessionFactory")
	@Primary
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("fxbDataSource") DataSource fxbDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(fxbDataSource);
		 // 分页拦截器-begin
    /*    PageInterceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", helperDialect);
        properties.setProperty("offsetAsPageNum", offsetAsPageNum);
        interceptor.setProperties(properties);*/
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("offsetAsPageNum", "true");  
        properties.setProperty("rowBoundsWithCount", "true"); 
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        //添加插件
        sessionFactory.setPlugins(new Interceptor[]{(Interceptor) pageHelper});
       // sessionFactory.getObject().getConfiguration().addInterceptor(interceptor);
        sessionFactory.getObject().getConfiguration().addInterceptor(sqlPrintInterceptor());
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MasterDataSourceConfig.MAPPER_LOCATION));
		 
	 
		return sessionFactory.getObject();
	}
 
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	// 将要执行的sql进行日志打印(不想拦截，就把这方法注释掉)
	@Bean
	public SqlPrintInterceptor sqlPrintInterceptor() {
		return new SqlPrintInterceptor();
	} 
	
}
