**基于SpringBoot实现后台管理系统**

* 后端： SpringBoot-2.x + Redis-4.x

* 前端： Bootstrap + Jquery

**测试环境**

* IDEA + Maven-10.13 + Tomcat8 + JDK8

**启动说明**

* 启动前，请配置好 src/main/resources/application.yml 中连接数据库的用户名和密码，以及Redis服务器的地址和端口信息。

* 启动前，请创建数据库`quick_master`，建表SQL语句放在：[/db/quick_master.sql]，具体的建表和建库语句请仔细看SQL文件。

* 配置完成后，运行位于 `src/main/com/ron/`下的QuickMasterApplication中的main方法，访问 `http://localhost:8080/login/` 进行测试。


**写在前面**

SpringBoot不是对Spring功能上的增强，而是提供了一种快速使用Spring的方式，所以本质上和SSM框架差别不大，所以学习此项目不仅可以学习到后台系统的设计流程还能很好的练习一下SpringBoot框架。当然如果你对SpringBoot框架不是很熟悉的话，我推荐你你看一下：

*本系统从2019/11/7日起，会一直持续更新，欢迎各位Spring系统的使用者一起沟通、学习！

*热烈欢迎 star(#^.^#)


**项目设计**

```
.
├── README  -- Doc文档
├── db  -- 数据库约束文件
├── mvnw  
├── mvnw.cmd
├── pom.xml  -- 项目依赖
└── src
    ├── main
    │   ├── java
    │   │   └── com.ron
    │   │       └── structure
    │   │           ├── QuickMasterApplication.java  -- SpringBoot启动器
    │   │           ├── controller  -- MVC的web层
    │   │           ├── dto  -- 统一封装的一些结果属性，和entity类似
    │   │           ├── entity  -- 实体类
    │   │           ├── utils  -- 各种工具类
    │   │           ├── exception  -- 统一的异常结果
    │   │           ├── mapper  -- Mybatis-Mapper层映射接口，或称为DAO层
    │   │           ├── common  -- 通常工作类，如常量、枚举等
    │   │           ├── config  -- redis,jedis 相关配置
    │   │           └── service  -- 业务层
    │   └── resources
    │       ├── application.yml  -- SpringBoot核心配置
    │       ├── mapper  -- Mybatis-Mapper层XML映射文件
    │       ├── static  -- 存放页面静态资源，可通过浏览器直接访问
    │       │   ├── css
    │       │   ├── js
    │       │   └── lib
    │       └── templates  -- 存放Thymeleaf模板引擎所需的HTML，不能在浏览器直接访问
    │           ├── page
    │           └── public  -- HTML页面公共组件（头部、尾部）
    └── test  -- 测试文件
```
