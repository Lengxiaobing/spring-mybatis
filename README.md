# MyBatis学习程序

> 官文文档：[MyBatis](<http://www.mybatis.org/mybatis-3/zh/index.html>)

## 程序模块：
> 测试程序在test目录下

- mybatis-dao：原始DAO的开发方式；
- mybatis-mapper：Mapper代理方式。

## MyBatis开发DAO的两种方式
- 第一种：原始DAO的开发方式；
- 第二种：Mapper代理的方式。

**Mapper接口开发需要遵循以下规范**：

- Mapper.xml文件中的namespace与mapper接口的类路径相同；
- Mapper接口方法名和Mapper.xml中定义的每个statement的id相同；
- Mapper接口方法的输入参数类型和mapper.xml中定义的每个sql的parameterType的类型相同；
- Mapper接口方法的输出参数类型和mapper.xml中定义的每个sql的resultType的类型相同。

## XML映射文件
1. **namespace**
	- 在原始DAO的方式中，是对sql进行分类化管理，可以理解为sql的隔离；
	- 在Mapper代理的方式中，是mapper接口的类路径。
2. 



## MyBatis配置文件
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

## $与#的区别

- **`#{}`**：解析传递进来的参数数据，相当于jdbc的占位符；
- **`${}`**：对传递进来的参数原样拼接在SQL中，即字符串拼接；
- **`#{}`**：是预编译处理；
- **`${}`**：是字符串替换；
- **`#{}`**：可以有效的防止SQL注入，提高系统安全性。