package com.study.cae.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = DataPlatformDataSourceConfig.PACKAGE, sqlSessionFactoryRef = DataPlatformDataSourceConfig.SQL_SESSION_FACTORY_BEAN_NAME)
public class DataPlatformDataSourceConfig {

    static final String DATA_SOURCE_BEAN_NAME = "dataPlatformDataSource";
    static final String SQL_SESSION_FACTORY_BEAN_NAME = "dataPlatformSqlSessionFactory";
    static final String TRANSACTION_MANAGER_BEAN_NAME = "dataPlatformTransactionManager";
    static final String DATA_SOURCE_PREFIX = "spring.datasource";

    static final String PACKAGE = "com.mybatis.plus.mapper*";
    static final String MAPPER_LOCATION = "classpath:mybatis/*Mapper.xml";

    @Bean(name = DATA_SOURCE_BEAN_NAME)
    @ConfigurationProperties(prefix = DATA_SOURCE_PREFIX)
    public DataSource dataPlatformDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name = TRANSACTION_MANAGER_BEAN_NAME)
    public DataSourceTransactionManager dataPlatformTransactionManager() {
        return new DataSourceTransactionManager(dataPlatformDataSource());
    }

    @Bean(name = SQL_SESSION_FACTORY_BEAN_NAME)
    public MybatisSqlSessionFactoryBean dataPlatformSqlSessionFactory(@Qualifier(DATA_SOURCE_BEAN_NAME) DataSource dataSource)
            throws Exception {
        final MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));

        //设置分页的拦截器
        PageInterceptor pageInterceptor = new PageInterceptor();
        //创建插件需要的参数集合
        Properties properties = new Properties();
        pageInterceptor.setProperties(properties);
        //将拦截器设置到sqlSessionFactroy中
        bean.setPlugins(new Interceptor[]{pageInterceptor});
        /*设置打印sql*/
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(StdOutImpl.class);
        bean.setConfiguration(configuration);

        return bean;
    }

}