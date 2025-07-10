# LeadNews 后台管理系统

这是一个基于 Spring Boot 微服务架构实现的内容管理系统（CMS）后台项目，包含用户管理、内容审核、文章发布、数据统计等核心功能模块。

## 技术栈

- Java 8
- Spring Boot / Spring Cloud Alibaba
- MySQL / Redis
- Nacos 注册中心
- RabbitMQ 消息队列
- Elasticsearch 搜索引擎
- Docker（支持容器部署）
- Git / GitHub

## 模块说明

| 模块名               | 描述                        |
|----------------------|-----------------------------|
| leadnews-api         | 统一接口定义层              |
| leadnews-common      | 公共模块（常量、工具类等）  |
| leadnews-common-db   | 数据库操作工具封装          |
| leadnews-core        | 业务核心模块                |
| leadnews-gateway     | Spring Cloud 网关           |
| leadnews-service     | 服务模块集成入口            |

## 数据库配置（仅供开发测试）

数据库地址为内网地址（192.168.211.128），用户名为 `root`，密码为 `123456`。  
项目部署仅限个人局域网学习使用。

## 启动方式

1. 安装并启动 MySQL、Redis、Nacos 等服务
2. 修改 `application.yml` 中相关配置
3. 使用 IDEA 或命令行运行 `leadnews-gateway` 模块
4. 访问接口进行调试或开发

## 注意事项

⚠️ 此项目已移除云服务密钥等敏感信息，部署请使用自己的配置。

---

项目仅用于个人学习和申请使用，非商用。
