package com.xxl.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableTransactionManagement
@Configuration
@MapperScan(value = {"com.xxl.mapper.read.*,com.xxl.mapper.write.*,com.xxl.mapper.mp.*"})//指定对应的mapper路径
public class MybatisPlusConfig {
    private static ApplicationContext applicationContext;

    @Autowired
    MybatisPlusNacosConfiguration mybatisPlusNacosConfiguration;
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    public DataSource db1() {
        return new DruidDataSource();
    }

    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.read")
    public DataSource db2() {
        return new DruidDataSource();
    }

    /**
     * 动态数据源配置
     *
     * @return
     */
    @Bean(name = "multipleDataSource")
    @Primary
    public DataSource multipleDataSource(@Qualifier("db1") DataSource db1,
                                         @Qualifier("db2") DataSource db2) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER.getValue(), db1);
        targetDataSources.put(DBTypeEnum.READ.getValue(), db2);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 程序默认数据源，根据程序调用数据源频次，把常调用的数据源作为默认
        dynamicDataSource.setDefaultTargetDataSource(db1);
        return dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(db1(), db2()));
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*/*.xml"));
        sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor()});
        /**
         * Mybatis配置
         */
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(mybatisPlusNacosConfiguration.getMapUnderscore2CamelCase());
        configuration.setCallSettersOnNulls(mybatisPlusNacosConfiguration.getCallSettersOnNulls());
        configuration.setCacheEnabled(false);
        // 数据库查询结果驼峰式返回
        sqlSessionFactory.setObjectWrapperFactory(new MybatisMapWrapperFactory());
        // 添加分页功能
        sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor()});

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(mybatisPlusNacosConfiguration.getBanner());
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();

        dbConfig.setIdType(IdType.valueOf(mybatisPlusNacosConfiguration.getIdType()));
        globalConfig.setDbConfig(dbConfig);

        configuration.setGlobalConfig(globalConfig);

        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setGlobalConfig(globalConfig);
        return sqlSessionFactory.getObject();
    }

    @Bean(name = "multipleTransactionManager")
    @Primary
    public DataSourceTransactionManager multipleTransactionManager(@Qualifier("multipleDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.POSTGRE_SQL);
        paginationInnerInterceptor.setMaxLimit(500L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}

