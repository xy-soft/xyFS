# xyFS私有OSS文件存储服务

#### 项目介绍
一站式企业私有文件服务。针对软件开发时提供的文件存储系统,对文件上传、下载、分类、分组、审计、统计等操作进行业务剥离,支持企业内多项目，提供统一的oss私有文件服务。
<br>
涵盖了保护企业隐私文件的私有存储，也可接入公有云存储方案。开发初衷是应用于xyTalk企业IM即时通讯的离线文件存储、群文件、图片、短语音、短视频、企业文档库等应用。
<br>
xyTalk地址：https://gitee.com/475660/xyTalk-pc
<br>
xyFS不仅仅是独立可用的文件系统，还通过接口灵活集成以下服务：

- 1.私有Xy.File OSS-FS文件服务 （自主开发，免费开源） 
- 2.私有分布式文件：MongoDB GridFS分布式文件系统 
- 3.私有分布式文件：FastDFS文件系统 
- 4.私有分布式文件：SeaweedFS 
- 5.私有企业云存储：Seafile网盘 
- 6.私有BASE64转存关系数据库存储（停用） 
- 7.公有云存储：阿里OSS云存储 
- 8.公有云存储：七牛云 
- 9.公有云存储：腾讯文件云存储 CFS 

#### 项目详细文档

http://xyfs.mydoc.io/


#### 软件组成


- 1.xyFS 管理后端；
- 2.xyPortal Web前端中的“文档库”部分；
- 3.xyFS HTTP API；
- 4.xyFS JAVA Client；

#### 采用技术

- 1.Spring Boot、MVC、AOP、Actuator、Swagger
- 2.Dbcp2、Druid，数据库连接池2种模式可选
- 3.JPA/Hibernate
- 4.Ehcache
- 5.HTML、Layer、Bootstrap
- 6.Tomcat
- 7.MySQL、Oracle、SQLite，3种数据库模式可选
- 8.Gradle
- 9.Nginx

#### 安装、使用教程

- 1、Import Gradle Project；
- 2、点击工程，右键Gradle>>Refresh Gradle Project；
- 3、选择Gradle Task栏，点击“bootRun”；
- 4、如果出现以下信息，说明运行时环境已经完成：
<br>
2018-07-09 16:06:56,790 INFO (TomcatWebServer.java:206)- Tomcat started on port(s): 9091 (http) with context path ''
<br>
2018-07-09 16:06:56,794 INFO (StartupInfoLogger.java:59)- Started FileSystemApplication in 14.575 seconds (JVM running for 15.457)
<br>
2018-07-09 16:07:18,809 INFO (DirectJDKLog.java:180)- Initializing Spring FrameworkServlet 'dispatcherServlet'
<br>
这时就可以使用啦，访问：
<br>
http://localhost:9091/
<br>
如要了解Gradle编译和运行更多事宜，请访问Spring Boot官网文章：Building Java Projects with Gradle
地址：https://spring.io/guides/gs/gradle/

### 开发配置
可以在配置文件application.properties里修改属性

例如
tomcat服务端口
server.port=9091

是否重新命名文件名，如果是个人文件则重命名为：username_ + 原文件名；如果是群组文件则重命名为：groupid_ + 原文件名     
storage.rename = true

存储源.可多选.可以同时储存多个数据源,便于备份      
类型有：xyfs\\Seafile\\FastDFS\\SeaweedFS\\MongoDB\\aliOSS\\qiniu\\CFS        
storage.todisk = true  
storage.toqiniu = true
storage.tofastdfs = false
storage.tomongodb = false
storage.toseaweedfs = false
storage.toalioss = false
storage.tocfs = false

设置下载的源 ,单选  
类型有：xyfs\\Seafile\\FastDFS\\SeaweedFS\\MongoDB\\aliOSS\\qiniu\\CFS       
storage.downloadfrom = xyfs

