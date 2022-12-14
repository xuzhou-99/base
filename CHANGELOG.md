# Base 通用基础包

项目基于SpringBoot，封装常用的基础服务模块

* 接口请求封装服务
* Http Client连接池服务
* Spring 容器服务
* 基础工具类
    * 邮件工具类
    * Id生成工具类-UUID(唯一有序)、SID(唯一有序自增、分布)
    * 日期、请求、IO工具类

## v1.0.2

2022-12-14

### Feature

* 迁移 SpringBeanUtils 从util到spring包
* 添加Spring容器管理，可自定义系统加载时执行事件、Bean集合存放
* 邮件服务改为 com.sun.mail 依赖，接口api升级
* 部分代码优化

## v1.0.1

2022-09-27

### Feature

* 添加项目Readme
* 优化接口返回封装

### BugFixed

* 修复HttpClient配置不生效的问题

## v1.0.0

2022-04-20

### Feature

* 创建基础包项目
* 添加 HttpClient 连接池服务及Http请求工具类 HttpClientUtils.java
* 添加请求封装，用于请后接口返回封装 ApiResponse.java、ApiDataResponse.java
* 添加Id生成工具类，UUID(唯一有序)、SID(唯一有序自增、分布)
* 添加Spring容器工具类
* 添加IO工具类

