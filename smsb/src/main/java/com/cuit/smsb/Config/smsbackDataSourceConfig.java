package com.cuit.smsb.Config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = smsbackDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "smsbackSqlSessionFactory")
public class smsbackDataSourceConfig {
    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.cuit.smsb.Mapper";
    static final String MAPPER_LOCATION = "classpath:Mapper/*.xml";
    @Value("${smsback.datasource.url}")
    private String url;

    @Value("${smsback.datasource.username}")
    private String user;

    @Value("${smsback.datasource.password}")
    private String password;

    @Value("${smsback.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "smsbackDataSource")
    @Primary
    public DataSource smsbackDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "smsbackTransactionManager")
    @Primary
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(smsbackDataSource());
    }

    @Bean(name = "smsbackSqlSessionFactory")
    @Primary
    public SqlSessionFactory smsbackSqlSessionFactory(@Qualifier("smsbackDataSource") DataSource smsbackDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(smsbackDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(smsbackDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
