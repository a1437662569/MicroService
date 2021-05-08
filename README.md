#云启项目说明

    公司SpringMVC迁移到SpringCloud的技术预研，本项目作为公共项目集成业务所需各个组件。
    经验证：注释掉部分CRUD业务代码后可正常使用

##技术选型

| 技术      | 选型     | 集成情况     | 
| ---------- | :-----------:  | :-----------:  |
| SpringBoot               | 2.2.2     |OK     |
| SpringBoot Alibaba            | 1.0.0     |OK     |
| SpringCloud               | Hoxton.SR1     |OK     |
| SpringCloud Alibaba               | 2.1.0     |OK     |
| 依赖管理与构建               | maven     |OK     |
| 数据库               | PostgreSQL     |OK     | 
| 数据库连接池               | Druid     |OK     |
| 数据库中间件           | MybatisPlus     |OK     |
| 多数据源              | MybatisPlusDS     |OK     |
| 注册中心&配置中心     | Nacos           |OK     |
| NoSQL       | Redis     |OK     |
| 网关       | SpringCloud Gateway     |OK     |
| 服务调用       | OpenFeign+Dubbo     |OK     |
| 服务降级与熔断       | Sentinel     |待集成     |
| 分布式事务       | Seata     |待集成     |
| 服务调用链       | 待选型     |待集成     |

##注意事项
###1、日志打印
    在进行切面打印日志时
    1、spring-boot-starter-web使用常规
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    即可获取
    
    2、spring-boot-starter-webflux需要百度webflux获取
    
    
