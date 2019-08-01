# MyBatis学习程序

> 官文文档：[MyBatis](<http://www.mybatis.org/mybatis-3/zh/index.html>)

MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

## 程序模块

> 测试程序在test目录下

- mybatis-dao：原始DAO的开发方式；
- mybatis-mapper：Mapper动态代理方式；
- mybatis-spring：mybatis和spring整合。

## MyBatis开发DAO的两种方式

**使用MyBatis开发DAO有两个方法**：

- 第一种：原始DAO的开发方式；
- 第二种：Mapper动态代理的方式。

**原始DAO的开发方式存在的问题：**

- dao接口实现类方法中存在大量重复代码，从设计上来看，应该抽取。
- 调用sqlSession方法时，将satement的id硬编码了，即类似于”user.findUserById”这种，都写死了。

- sqlSession方法中，要求传入的参数是Object类型(泛型)。如果传错了参数，编译不会报错，执行时才会报错，不利于开发。

**Mapper接口开发需要遵循的规范**：

- Mapper.xml文件中的namespace与mapper接口的类路径相同；
- Mapper接口方法名和Mapper.xml中定义的每个statement的id相同；
- Mapper接口方法的输入参数类型和mapper.xml中定义的每个sql的parameterType的类型相同；
- Mapper接口方法的输出参数类型和mapper.xml中定义的每个sql的resultType的类型相同。

## MyBatis配置文件

> MyBatis 的[配置文件](<http://www.mybatis.org/mybatis-3/zh/configuration.html#>)包含了影响MyBatis行为的设置和属性信息，元素有固定的顺序，不可颠倒。

- 一个完全的mybatis配置文件结构如下：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
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

### properties

> 属性，可外部配置且可动态替换的，既可以在Java 属性文件中配置，亦可通过 properties 元素的子元素来传递。

如果在多个地方配置了属性，MyBatis 将按照下面的顺序来加载：

- 首先，读取**配置文件**中 **properties** 元素体内设置的属性。
- 其次，读取 **properties** 元素中的 **resource** (**类路径**)属性指定的配置文件，或者 **url** (**绝对路径**)属性指定的配置文件中的属性，并覆盖上一步已读取的同名属性。

- 最后，读取**映射文件**中方法参数（**parameterType**）传递的属性，并覆盖上一步已读取的同名属性。

因此，通过方法参数传递的属性具有最高优先级，resource/url 属性中指定的配置文件次之，最低优先级的是 properties 属性中指定的属性。

**`注意`**：优先级高的属性会覆盖优先级低的属性

### settings

>全局配置，改变 MyBatis 的运行时行为。

例如：开启二级缓存、开启延时加载等等。

```xml
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
</settings>
```

### typeAliases

> 类型别名，为 Java 类型设置一个短的名字。 它只和 XML 配置有关，存在的意义仅在于用来减少类完全限定名的冗余。

- 单个别名定义

```xml
<typeAliases>
  <!-- type：类路径  alias：别名 -->
  <typeAlias type="domain.blog.Author" alias="Author"/>
  <typeAlias type="domain.blog.Blog" alias="Blog"/>
</typeAliases>
```

- 批量别名定义

```xml
<typeAliases>
    <!-- name：指定包名，MyBatis自动扫描包中的类，别名就是类名（首字母不区分大小写） -->
  <package name="domain.blog"/>
</typeAliases>
```

### typeHandlers

> 类型处理器，无论是在预处理语句（PreparedStatement）中设置一个参数时，还是从结果集中取出一个值时， 都会用类型处理器，将获取的值以合适的方式转换成 Java 类型。

一般情况下，MyBatis提供的类型处理器就满足使用需要，不需要在自定义了。

如果需要自定义，分为两个步骤

- 实现 `org.apache.ibatis.type.TypeHandler` 接口， 或继承类 `org.apache.ibatis.type.BaseTypeHandler`。

- 配置typeHandlers属性

### objectFactory

> 对象工厂，MyBatis 每次创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成。 默认的对象工厂需要做的仅仅是实例化目标类，要么通过默认构造方法，要么在参数映射存在的时候通过参数构造方法来实例化。 如果想覆盖对象工厂的默认行为，则可以通过创建自己的对象工厂来实现。

ObjectFactory 接口很简单，它包含两个创建用的方法，一个是处理默认构造方法的，另外一个是处理带参数的构造方法的。 最后，setProperties 方法可以被用来配置 ObjectFactory，在初始化你的 ObjectFactory 实例后， objectFactory 元素体中定义的属性会被传递给 setProperties 方法。

### plugins

> 插件，MyBatis 允许在已映射语句执行过程中的某一点进行拦截调用。

默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)

**`提示`** **覆盖配置类**

除了用插件来修改 MyBatis 核心行为之外，还可以通过完全覆盖配置类来达到目的。只需继承后覆盖其中的每个方法，再把它传递到 SqlSessionFactoryBuilder.build(myConfig) 方法即可。再次重申，这可能会严重影响 MyBatis 的行为，务请慎之又慎。

### environments

> 环节配置，MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中。

现实情况下有多种理由需要这么做。例如，开发、测试和生产环境需要有不同的配置；或者想在具有相同 Schema 的多个生产数据库中 使用相同的 SQL 映射。

**注意：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

所以，如果想连接两个数据库，就需要创建两个 SqlSessionFactory 实例，依此类推。

**配置环境示例**：

```xml
<environments default="development">
  <environment id="development">
    <transactionManager type="JDBC">
      <property name="..." value="..."/>
    </transactionManager>
    <dataSource type="POOLED">
      <property name="driver" value="${driver}"/>
      <property name="url" value="${url}"/>
      <property name="username" value="${username}"/>
      <property name="password" value="${password}"/>
    </dataSource>
  </environment>
</environments>
```

#### transactionManager

在 MyBatis 中有两种类型的事务管理器：

- JDBC – 这个配置就是直接使用了 JDBC 的提交和回滚设置，它依赖于从数据源得到的连接来管理事务作用域。
- MANAGED – 这个配置几乎没做什么。它从不提交或回滚一个连接，而是让容器来管理事务的整个生命周期。

**提示**：如果使用 Spring + MyBatis，则没有必要配置事务管理器， 因为 Spring 模块会使用自带的管理器来覆盖前面的配置。

#### dataSource

dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。

有三种内建的数据源类型：

- **UNPOOLED**– 这个数据源的实现只是每次被请求时打开和关闭连接。
- **POOLED**– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。
- **JNDI** – 这个数据源的实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的引用。

### databaseIdProvider

> 数据库厂商标识，MyBatis可以根据不同的数据库厂商执行不同的语句，这种多厂商的支持是基于映射语句中的`databaseId`属性。

MyBatis 会加载不带 `databaseId` 属性和带有匹配当前数据库 `databaseId` 属性的所有语句。 如果同时找到带有 `databaseId` 和不带 `databaseId` 的相同语句，则后者会被舍弃。

```xml
<!-- 使用完全限定资源定位符（URL） -->
<databaseIdProvider type="DB_VENDOR">
  <!-- 设置属性别名 -->
  <property name="SQL Server" value="sqlserver"/>
  <property name="DB2" value="db2"/>
  <property name="Oracle" value="oracle" />
</databaseIdProvider>
```

### mapper

> 映射器，告诉了 MyBatis 去哪里找映射文件，可以使用相对于类路径的资源引用， 或完全限定资源定位符（包括 `file:///` 的 URL），或类名和包名等

几种配置示例如下：

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

## XML映射文件

> MyBatis 的真正强大在于它的映射语句，这是它的魔力所在。由于它的异常强大，映射器的 [XML文件](<http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html>)就显得相对简单。如果拿它跟具有相同功能的 JDBC 代码进行对比，你会立即发现省掉了将近 95% 的代码。MyBatis 为聚焦于 SQL 而构建，以尽可能地为你减少麻烦。

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

### 元素说明

- **namespace**：命名空间
  - 在原始DAO的方式中，是对sql进行分类化管理，可以理解为sql的隔离；
  - 在Mapper动态代理的方式中，是mapper接口的类路径。

## $与#的区别

- **`#{}`**：解析传递进来的参数数据(**占位符**)；
- **`${}`**：对传递进来的参数原样拼接在SQL中(**拼接符**)；
- **`#{}`**：是预编译处理；
- **`${}`**：是字符串替换；
- **`#{}`**：可以有效的防止SQL注入，提高系统安全性。

## 输入映射

输入类型常用的有如下几种类型：

- 传递简单类型

- 传递pojo对象

- 传递pojo包装类型（使用OGNL语法）

- 传递HashMap

## 输出映射

- **resultType**
  - 使用resultType进行输出映射时，只有查询出来的列名和pojo中的属性名一致，该列才可以映射成功。
  - 如果结果只有一条并只有一列，可以使用简单类型进行输出映射
  - 输出pojo单个对象或者pojo集合列表时，resultType指定的输出类型是一样的，但是返回值类型不同。
  - 输出类型为hashmap时，将输出的字段名称作为map的key，字段值作为value，多条时为List<HashMap<k,v>>。

**`注意`**：生成的动态代理对象是根据mapper方法的返回值类型，确定是调用selectOne还是selectList。

- resultMap

  - 查询出来的列名和pojo的属性名不一致时，通过定义一个resultMap对列名和属性名之间作一个映射关系。

**`注意`**：如果resultMap在不同的namespace中，使用时需要在上namespace

## 动态SQL

> MyBatis 的强大特性之一便是它的动态 SQL。动态 SQL 元素和 JSTL 或基于类似 XML 的文本处理器相似。MyBatis 采用功能强大的基于 OGNL 的表达式来淘汰其它大部分元素。

动态SQL元素：

- if
- choose (when, otherwise)
- trim (where, set)
- foreach

### if

> 动态 SQL 通常要做的事情是根据条件包含 where 子句的一部分。比如：

```xml
<select id="findActiveBlogWithTitleLike"
     resultType="Blog">
  SELECT * FROM BLOG
  WHERE state = ‘ACTIVE’
  <if test="title != null">
    AND title like #{title}
  </if>
</select>
```

### choose, when, otherwise

> 有时不想应用到所有的条件语句，而只想从中择其一项。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。

```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```

### trim, where, set

- **where**

> where 元素只会在有if条件满足的情况下才会生成“WHERE”子句。并且，若语句的开头为“AND”或“OR”，where元素会自动去除。

```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG
  <where>
    <if test="state != null">
         state = #{state}
    </if>
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```

- **trim**

> trim 元素可以用来定制元素功能。它的作用是移除prefixOverrides 属性中指定的内容，并且插入prefix 属性中指定的内容。（类似于替换功能）

```xml
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ...
</trim>
```

- **set**

> set元素会动态前置 SET 关键字，同时也会删掉无用的逗号。因为用了条件语句之后很可能就会在生成的 SQL 语句的后面留下逗号。

```xml
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>
```

### foreach

> 动态 SQL 的另外一个常用的操作需求是对一个集合进行遍历，通常是在构建 IN 条件语句的时候。

```xml
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="item" index="index" collection="list"
      open="(" separator="," close=")">
        #{item}
  </foreach>
</select>
```

foreach元素的功能非常强大，它允许指定一个集合，声明可以在元素体内使用的集合项（item）和索引（index）变量。它也允许指定开头(open)与结尾(close)的字符串以及在迭代结果（separator）之间放置分隔符。

**注意** 可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象传递给 foreach 作为集合参数。当使用可迭代对象或者数组时，index 是当前迭代的次数，item 的值是本次迭代获取的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。

## 高级映射

### 一对一查询

**resultType方式**

```xml
<select id="findOrdersList" resultType="cn.itcast.mybatis.po.OrdersCustom">    
    SELECT    
        orders.*,    
        user.username,    
        user.address
    FROM    
    	orders,user    
    WHERE orders.user_id = user.id
</select>
```

**resultMap方式**

```xml
<resultMap type="cn.itcast.mybatis.po.Orders"id="userOrderMap">
    <id property="id" column="id"/>
    <result property="user_id" column="user_id"/>
    <result property="number" column="number"/>
    <association property="user" javaType="cn.itcast.mybatis.po.User">
        <id property="id" column="user_id"/>
        <result property="username" column="username"/>
        <result property="address" column="address"/>
    </association>
</resultMap>
```

**总结**：

- **resultType**

  - 使用resultType实现较为简单，如果pojo中没有包括查询出来的列名，只需增加列名对应的属性，即可完成映射。

  - 如果查询结果没有特殊的要求建议使用resultType。
- **resultMap**

  - 实现有点麻烦，需要单独定义resultMap，如果对查询结果有特殊的要求，可以使用resultMap将查询到的列名，映射到关联的pojo属性中。

**`注意`**：resultMap可以实现延迟加载，resultType无法实现延迟加载。

### 一对多查询

**resultMap方式**

```xml
<resultMap　type="cn.itcast.mybatis.po.Orders"　id="userorderdetailmap">    
　　<id property="id"column="id"/>    
　　<result property="user_id" column="user_id"/>    
　　<result property="number" column="number"/>    
　　<association property="user" javaType="cn.itcast.mybatis.po.User">    
　　　　<id property="id" column="user_id"/>    
　　　　<result property="username" column="username"/>    
　　　　<result property="address" column="address"/>    
　　</association>    
　　<collection　property="orderdetails" ofType="cn.itcast.mybatis.po.Orderdetail">    
　　　　<id property="id" column="orderdetail_id"/>    
　　　　<result property="items_id" column="items_id"/>    
　　　　<result property="items_num" column="items_num"/>    
	</collection>    
</resultMap>
```

**总结**：

- 使用resultMap的association属性，可以将关联查询信息映射到一个pojo对象中。

- 使用resultMap的collection属性，可以将关联查询到的多条记录映射到一个集合属性中。
- resultMap具有继承特性，可以继承已定义的resultMap。

- 使用resultType实现：需要自己处理，使用双重循环遍历，去掉重复记录，将查询到的多条记录放到集合属性中。

### 多对多查询

**resultMap方式**

```xml
<resultMap type="cn.itcast.mybatis.po.User"id="userOrderListResultMap">    
       <id column="user_id"property="id"/>    
       <result column="username"property="username"/>    
       <collection property="orders"ofType="cn.itcast.mybatis.po.Orders">    
          <id  column="id"property="id"/>    
          <result property="number" column="number"/>    
          <collection property="orderdetails"ofType="cn.itcast.mybatis.po.Orderdetail">    
               <id  column="orderdetail_id" property="id"/>    
               <result property="ordersId"column="id"/>    
               <result property="itemsId"column="items_id"/>    
               <result property="itemsNum"column="items_num"/>    
               <association property="items"javaType="cn.itcast.mybatis.po.Items">    
                   <id column="items_id" property="id"/>    
                   <result column="items_name" property="name"/>    
                   <result column="items_detail" property="detail"/>    
              </association>    
         </collection>    
     </collection>    
</resultMap>  
```

**总结**：

- 使用collection和association分别对集合和实体进行关联映射，它们层层嵌套的方式，就跟实体之间层层嵌套的方式一样。

## 延时加载

先从单表查询、需要时再从关联表去关联查询，大大提高数据库性能，因为查询单表要比关联查询多张表速度要快。

## 查询缓存

### 一级缓存

一级缓存是SqlSession级别的缓存。在操作数据库时需要构造 sqlSession对象，在对象中有一个(内存区域)数据结构（HashMap）用于存储缓存数据。不同的sqlSession之间的缓存数据区域是互不影响的。

一级缓存的作用域是同一个SqlSession，在同一个sqlSession中多次执行相同的sql语句时，第一次执行完会将查询到的数据写入缓存（内存），下次会从缓存中获取数据，将不再从数据库查询，从而提高查询效率。当一个sqlSession结束后，该sqlSession中的一级缓存也就不存在了。Mybatis默认开启一级缓存。

如果sqlSession去执行commit操作（插入、更新、删除），将会清空SqlSession中的一级缓存，这样做的目的为了让缓存中存储的是最新的信息，避免脏读。

**`注意`**：将mybatis和spring进行整合开发时，事务控制在service中。一个service方法中包括很多mapper方法调用。如果是执行两次service调用查询，不走一级缓存，因为Service方法结束，sqlSession就关闭，一级缓存就清空。

### 二级缓存

二级缓存是mapper级别的缓存，多个SqlSession去操作同一个Mapper的sql语句时，查询到的数据会存在二级缓存区域，多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。

二级缓存是多个SqlSession共享的，其作用域是mapper的同一个namespace，不同的sqlSession多次执行相同namespace下的sql语句，且向sql中传递参数也相同（即执行相同的sql语句）时，第一次执行完毕会将查询到的数据写入缓存（内存），下次会从缓存中获取数据将不再从数据库查询，从而提高查询效率。

Mybatis默认没有开启二级缓存，需要在setting全局参数中配置开启二级缓存。

二级缓存需要pojo对象实现java.io.Serializable接口实现序列化和反序列化操作。
