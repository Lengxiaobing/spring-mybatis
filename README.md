# MyBatis学习程序

> 官文文档：[MyBatis](<http://www.mybatis.org/mybatis-3/zh/index.html>)

MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

## 程序模块：

> 测试程序在test目录下

- mybatis-dao：原始DAO的开发方式；
- mybatis-mapper：Mapper动态代理方式。

## MyBatis开发DAO的两种方式

**使用MyBatis开发DAO有两个方法**：

- 第一种：原始DAO的开发方式；
- 第二种：Mapper动态代理的方式。

**Mapper接口开发需要遵循以下规范**：

- Mapper.xml文件中的namespace与mapper接口的类路径相同；
- Mapper接口方法名和Mapper.xml中定义的每个statement的id相同；
- Mapper接口方法的输入参数类型和mapper.xml中定义的每个sql的parameterType的类型相同；
- Mapper接口方法的输出参数类型和mapper.xml中定义的每个sql的resultType的类型相同。

## MyBatis配置文件

> MyBatis 的配置文件包含了影响MyBatis 行为的设置和属性信息

一个完全的mybatis配置文件结构如下：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<!-- 配置文件的根元素 -->
<configuration>
    <!-- 属性：定义配置外在化 -->
    <properties></properties>
    <!-- 设置：定义mybatis的一些全局性设置 -->
    <settings>
       <!-- 具体的参数名和参数值 -->
       <setting name="" value=""/> 
    </settings>
    <!-- 类型名称：为一些类定义别名 -->
    <typeAliases></typeAliases>
    <!-- 类型处理器：定义Java类型与数据库中的数据类型之间的转换关系 -->
    <typeHandlers></typeHandlers>
    <!-- 对象工厂 -->
    <objectFactory type=""></objectFactory>
    <!-- 插件：mybatis的插件,插件可以修改mybatis的内部运行规则 -->
    <plugins>
       <plugin interceptor=""></plugin>
    </plugins>
    <!-- 环境：配置mybatis的环境 -->
    <environments default="">
       <!-- 环境变量：可以配置多个环境变量，比如使用多数据源时，就需要配置多个环境变量 -->
       <environment id="">
          <!-- 事务管理器 -->
          <transactionManager type=""></transactionManager>
          <!-- 数据源 -->
          <dataSource type=""></dataSource>
       </environment> 
    </environments>
    <!-- 数据库厂商标识 -->
    <databaseIdProvider type=""></databaseIdProvider>
    <!-- 映射器：指定映射文件或者映射类 -->
    <mappers></mappers>
</configuration>
```

## XML映射文件

> MyBatis 的真正强大在于它的映射语句，这是它的魔力所在。由于它的异常强大，映射器的 XML 文件就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 为聚焦于 SQL 而构建，以尽可能地为你减少麻烦。

**SQL 映射文件顶级元素**：

- `cache` – 对给定命名空间的缓存配置。
- `cache-ref` – 对其他命名空间缓存配置的引用。
- `resultMap` – 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。
- ~~`parameterMap` – 已被废弃！老式风格的参数映射。更好的办法是使用内联参数，此元素可能在将来被移除。~~
- `sql` – 可被其他语句引用的可重用语句块。
- `insert` – 映射插入语句
- `update` – 映射更新语句
- `delete` – 映射删除语句
- `select` – 映射查询语句

**元素说明**

1. **namespace**：命名空间
	- 在原始DAO的方式中，是对sql进行分类化管理，可以理解为sql的隔离；
	- 在Mapper动态代理的方式中，是mapper接口的类路径。
2. **insert**：插入标签



## $与#的区别

- **`#{}`**：解析传递进来的参数数据(**占位符**)；
- **`${}`**：对传递进来的参数原样拼接在SQL中(**拼接符**)；
- **`#{}`**：是预编译处理；
- **`${}`**：是字符串替换；
- **`#{}`**：可以有效的防止SQL注入，提高系统安全性。