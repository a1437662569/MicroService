#1、服务调用者404：
    Openfeign接口404
    No qualifying bean of type 'org.springframework.boot.autoconfigure.http.HttpMessageConverters'
#解决：
    #手动导入
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

#2、nacos配置：
Data ID：

    template-consumer-public.yaml
Group：
    
    DEFAULT_GROUP
配置格式： 
    
    YAML
配置内容如下(注意：spring需要顶左侧缩进)：

    spring:
        datasource:
            dynamic:
                primary: master
                strict: false
                datasource:
                    master:
                        url: jdbc:postgresql://192.168.1.133:23451/paycore?useUnicode=true&characterEncoding=UTF-8&useSSL=false
                        username: postgres
                        password: postgres
                        driver-class-name: org.postgresql.Driver
                        druid:
                            initial-size:
                            validation-query: select 11
                            public-key:
                    slave_1:
                        url: jdbc:postgresql://192.168.1.123:23451/paycore?useUnicode=true&characterEncoding=UTF-8&useSSL=false
                        username: postgres
                        password: postgres
                        driver-class-name: org.postgresql.Driver
                        druid:
                            initial-size:
                            validation-query: select 11
                            public-key:
        # Redis服务器地址.Redis单实例连接,更多参考:https://blog.csdn.net/lushuaiyin/article/details/105516690,https://www.codeleading.com/article/2360317473/
        redis:
            database: 0
            host: 192.168.1.133
            port: 6699  # Redis服务器连接端口
            password: wisecloud2018 # Redis服务器连接密码（默认为空）
            timeout: 100
            lettuce:
                pool:
                    max-active: -1 # 连接池最大连接数（使用负值表示没有限制）
                    max-wait: -1  # 连接池最大阻塞等待时间（使用负值表示没有限制）
                    max-idle: 8  # 连接池中的最大空闲连接
                    min-idle: 0  # 连接池中的最小空闲连接
        redis2:
            database: 1
            host: 192.168.1.133
            port: 6699  # Redis服务器连接端口
            password: wisecloud2018 # Redis服务器连接密码（默认为空）
            timeout: 100
            lettuce:
                pool:
                    max-active: -1 # 连接池最大连接数（使用负值表示没有限制）
                    max-wait: -1  # 连接池最大阻塞等待时间（使用负值表示没有限制）
                    max-idle: 8  # 连接池中的最大空闲连接
                    min-idle: 0  # 连接池中的最小空闲连接
        #solr配置
        data:
            solr:
                host: http://192.168.1.133:8984/solr/
                repositories-enabled: true
        #kafka相关参数配置
        kafka:
            listener:
                concurrency: 1
                missing-topics-fatal: false
                ack-mode: manual_immediate
                type: BATCH
            consumer:
                bootstrap-servers: 192.168.1.170:9092,192.168.1.171:9092,192.168.1.172:9092
                group-id: JavaGroup   #(消费组)
                enable-auto-commit: false #(是否自动提交)
                session-timeout: 200 #连接超时时间
                auto:
                    commit-interval: 1000
                    offset-reset: earliest # (实时生产，实时消费：latest，不会从头开始消费)
                max-poll:
                    records: 5
                    interval-ms: 1000
                key-serializer: org.apache.kafka.common.serialization.StringSerializer
                value-serializer: org.apache.kafka.common.serialization.StringSerializer
            ### producer 配置
            producer:
                bootstrap-servers: 192.168.1.170:9092,192.168.1.171:9092,192.168.1.172:9092
                key-serializer: org.apache.kafka.common.serialization.StringSerializer
                value-serializer: org.apache.kafka.common.serialization.StringSerializer
                retries: 0
                batch-size: 16384
                linger: 1
                buffer-memory: 40960
                session-timeout: 200 #连接超时时间
                max-block-ms: 100
    #MP配置文件详解:https://baomidou.com/config/
    mybatis-plus:
      mapper-locations: classpath:mapper/*.xml
      type-aliases-package: com.xxl.model
      configuration:
        #log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
        map-underscore-to-camel-case: true #实体接收自动转驼峰映射
        call-setters-on-nulls: true
      global-config:
        banner: false
        db-config:
            id-type: input
