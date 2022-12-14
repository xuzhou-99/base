# base 基础工具类应用包

项目基于SpringBoot，封装常用的基础服务模块

* 接口请求封装服务
* Http Client连接池服务
* Spring 容器服务
* 基础工具类
    * 邮件工具类
    * Id生成工具类-UUID(唯一有序)、SID(唯一有序自增、分布)
    * 日期、请求、IO工具类

### 项目介绍

`base`:基础应用包，常用工具、请求参数响应封装等.

**Licenses**        The Apache License, Version 2.0

**Home page**       https://github.com/xuzhou-99/base

**Source code**     https://github.com/xuzhou-99/base

**Developers**      xuzhou <xuzhou99@foxmail.com>

**Inception year**  2022

### 引入项目包

基于GitHub创建的个人Maven仓库

#### Maven

##### pom.xml

```xml

<repositories>
    <!-- github -->
    <repository>
        <id>github</id>
        <!-- https://raw.github.com/用户名/仓库名/分支名 -->
        <url>https://raw.github.com/xuzhou-99/mvn-repo/main</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

##### Apache Maven

```xml
<!-- 引入base依赖 -->
<dependency>
    <groupId>cn.altaria</groupId>
    <artifactId>base</artifactId>
    <version>1.0.2</version>
</dependency>
```
