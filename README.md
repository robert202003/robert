# robert

[![Codecov](https://codecov.io/gh/alibaba/spring-cloud-alibaba/branch/master/graph/badge.svg)](https://codecov.io/gh/alibaba/spring-cloud-alibaba)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

robert维护的一个项目，目前工作在深圳。

有关英文文档，请参见[English document](https://github.com/yulo2020/robert/blob/master/README-en.md)

robert致力于为微服务开发提供一个脚手架。 该项目包括用于开发分布式应用程序服务的必要组件，支持多应用程序访问，并使开发人员可以轻松地使用Spring Cloud编程模型来开发分布式应用程序服务。

使用Github，您只需要添加一些注释和少量配置即可将Spring Cloud应用程序连接到分布式应用程序解决方案，并使用此支架快速构建分布式应用程序系统。

## 主要功能
* **服务注册**：默认使用阿里的Nacos作为服务注册，生产环境可集群。
* **服务认证授权**：基于Spring Security和OAuth2框架，支持4种授权模式，分为服务端robert-auth-server和客户端robert-auth-client。
* **远程配置**：默认使用携程的Apollo作为远程配置服务，个人觉得比Nacos的配置好用。
* **分布式基础公共支持**：robert-core包含了常用的公共核心类和常见的中间件集成包，开箱即用。
* **网关服务**：以Spring Cloud官方的spring-cloud-gateway作为网关，支持动态路由、参数验签、token验证、限流和熔断等服务。
* **系统监控**：基于 spring-boot-admin-starter-server 为微服务应用实时监控的能力。
* **分布式事务**：基于阿里的Seata， 高效并且对业务几乎零侵入地解决分布式事务问题。
* **消息驱动能力**：基于 Spring Cloud Stream 为微服务应用构建消息驱动能力。
* **分布式唯一ID生成器**：分布式唯一ID生成器，增强版的雪花算法，高效唯一有规律，添加robert-id依赖即可使用。
* **公共包依赖**：集成了Redis，Mongo，RabbitMQ和RocketMQ，日志收集，添加相应的依赖即可使用。
* **服务链路追踪**：使用skywalking提供服务链路追踪，支持mysql持久化。
* **应用服务监控**：使用micrometer+prometheus+grafana作为应用服务的监控。业界良心



## 模块说明
```lua
robert                       -- 父项目，依赖管理
│  ├─robert-api-model        -- model依赖包
│  ├─robert-auth-client      -- oauth2客户端依赖包
│  ├─robert-auth-server      -- oauth2服务端[8050]
│  ├─robert-model            -- 公共的model依赖，包括vo,dto和feign等
│  ├─robert-common           -- 通用工程一级工程
│  │  ├─robert-core          --核心基础包
│  │  ├─robert-id            --分布式唯一ID生成器
│  │  ├─robert-log-record    --日志收集工具包
│  │  ├─robert-mongo         --mongo 集成，封装了很多mongo的操作
│  │  ├─robert-redis         --redis 操作集成，支持集群模式，包括了分布式锁的封装
│  │  ├─robert-stream        --stream 集成，集成RabbitMQ和RocketMQ
│  ├─robert-docs             -- 项目文档
│  ├─robert-gateway          -- 网关服务[9020]
│  ├─robert-modules          -- 业务模块一级工程
│  │  ├─robert-order-api         -- 订单服务[8030]
│  │  ├─robert-user-api          -- 用户中心[8020]
│  ├─robert-monitor          -- 监控中心

```

### 技术选型

#### 后端技术

| 技术                 | 说明                | 官网                                                 |
| -------------------- | ------------------- | ---------------------------------------------------- |
| SpringBoot           | 容器+MVC框架        | https://spring.io/projects/spring-boot               |
| SpringSecurity       | 认证和授权框架      | https://spring.io/projects/spring-security           |
| MyBatis              | ORM框架             | http://www.mybatis.org/mybatis-3/zh/index.html       |
| MyBatisGenerator     | 数据层代码生成      | http://www.mybatis.org/generator/index.html          |
| PageHelper           | MyBatis物理分页插件 | http://git.oschina.net/free/Mybatis_PageHelper       |
| Swagger-UI           | 文档生产工具        | https://github.com/swagger-api/swagger-ui            |
| Hibernator-Validator | 验证框架            | http://hibernate.org/validator                       |
| Elasticsearch        | 搜索引擎            | https://github.com/elastic/elasticsearch             |
| RabbitMQ             | 消息队列            | https://www.rabbitmq.com/                            |
| Redis                | 分布式缓存          | https://redis.io/                                    |
| MongoDB              | NoSql数据库         | https://www.mongodb.com                              |
| Docker               | 应用容器引擎        | https://www.docker.com                               |
| Druid                | 数据库连接池        | https://github.com/alibaba/druid                     |
| OSS                  | 对象存储            | https://github.com/aliyun/aliyun-oss-java-sdk        |
| MinIO                | 对象存储            | https://github.com/minio/minio                       |
| JWT                  | JWT登录支持         | https://github.com/jwtk/jjwt                         |
| LogStash             | 日志收集工具        | https://github.com/logstash/logstash-logback-encoder |
| Lombok               | 简化对象封装工具    | https://github.com/rzwitserloot/lombok               |
| Jenkins              | 自动化部署工具      | https://github.com/jenkinsci/jenkins                 |





