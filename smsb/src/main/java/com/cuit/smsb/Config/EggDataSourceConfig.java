package com.cuit.smsb.Config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = EggDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "eggSqlSessionFactory")
public class EggDataSourceConfig {
    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.cuit.smsb.eggMapper";
    static final String MAPPER_LOCATION = "classpath:eggMapper/*.xml";
    @Value("${egg.datasource.url}")
    private String url;

    @Value("${egg.datasource.username}")
    private String user;

    @Value("${egg.datasource.password}")
    private String password;

    @Value("${egg.datasource.driverClassName}")
    private String driverClass;

    @Bean(name = "eggDataSource")
    public DataSource eggDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean(name = "eggTransactionManager")
    public DataSourceTransactionManager eggTransactionManager() {
        return new DataSourceTransactionManager(eggDataSource());
    }
    @Bean(name = "eggSqlSessionFactory")
    public SqlSessionFactory eggSqlSessionFactory(@Qualifier("eggDataSource") DataSource eggDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(eggDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(EggDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}

