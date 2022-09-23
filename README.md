# base 基础工具类应用包

### 项目介绍

base

基础应用包，常用工具、请求参数响应封装等.

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
    <version>1.0.0</version>
</dependency>
```