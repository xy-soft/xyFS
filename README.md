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
- 5.HTML、 Only Bootstrap4。只使用了单纯的Bootstrap4，不再含其他任何前端UI框架。纯净、自然、轻量、极简、易维护。
- 6.Thymeleaf/Freemarker，2种模板引擎可切换
- 7.MySQL、Oracle、SQLite，3种数据库模式可选
- 8.其他：Gradle、Tomcat、Nginx

#### 截图预览
首页界面:
<br>
<img alt="XyTalk IM" src="http://111.230.157.216/img/index.png" />
文件管理:
<br>
<img alt="XyTalk IM" src="http://111.230.157.216/img/files.png" />
API管理:
<br>
<img alt="XyTalk IM" src="http://111.230.157.216/img/api.png" />
测试上传:
<br>
<img alt="XyTalk IM" src="http://111.230.157.216/img/upload.png" />

#### 安装、使用教程

- 1、Import Gradle Project；
- 2、点击工程，右键Gradle>>Refresh Gradle Project；
- 3、运行方法：右键“FileSystemApplication.java”，点击“run as”--“Java App..”。或者选择Gradle Task栏，点击“bootRun”；
- 4、如果出现以下信息，说明运行时环境已经完成：
<br>
2018-07-09 16:06:56,794 INFO (StartupInfoLogger.java:59)- Started FileSystemApplication in 14.575 seconds (JVM running for 15.457)
<br>
如果出现（Exception opening socket）localhost:27017异常 ，不必惊慌，只是mongodb未安装而已，不影响系统使用。
<br>
如要了解Gradle编译和运行更多事宜，请访问Spring Boot官网文章：Building Java Projects with Gradle
<br>
地址：https://spring.io/guides/gs/gradle/
<br>
这时就可以使用，访问：
<br>
http://localhost:9091/
<br>
			
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
- storage.todisk = true  
- storage.toqiniu = true
- storage.tofastdfs = false
- storage.tomongodb = false
- storage.toseaweedfs = false
- storage.toalioss = false
- storage.tocfs = false

设置下载的源 ,单选  
类型有：xyfs\\Seafile\\FastDFS\\SeaweedFS\\MongoDB\\aliOSS\\qiniu\\CFS       
- storage.downloadfrom = xyfs


### 全部配置：
<pre><code># tomcat服务端口         #
server.port=9091
#启用shutdown
endpoints.sensitive=false

# 存储的相对路径，如果 storage.source = xyfs 需要配置        #
storage.location = uploadfiledir

# 是否重新命名文件名，如果是个人文件则重命名为：username_ + 原文件名；如果是群组文件则重命名为：groupid_ + 原文件名     #
storage.rename = true

# 存储源.可多选.可以同时储存多个数据源,便于备份      #
# 类型有：xyfs\\Seafile\\FastDFS\\SeaweedFS\\MongoDB\\aliOSS\\qiniu\\CFS        #
storage.todisk = true  
storage.toqiniu = true
storage.tofastdfs = false
storage.tomongodb = false
storage.toseaweedfs = false
storage.toalioss = false
storage.tocfs = false

# 用于设置下载的源 ,单选   #
# 类型有：xyfs\\Seafile\\FastDFS\\SeaweedFS\\MongoDB\\aliOSS\\qiniu\\CFS        #
storage.downloadfrom = xyfs

# xyfs存储配置     #
storage.diskprefix = http://localhost:9091/files/

# 七牛存储配置     #
storage.qiniuprefix = http://pbby0yzdu.bkt.clouddn.com/
storage.qiniuak = _IAafy8aX5x7h-4FBEvH2DqCtTq2c7sESPSlfG
storage.qiniusk = _8hy2LE6kfTKr3wDUWJONgFRxPKX4cDQhi79Bj
storage.qiniubucket = xytalk

# mongodb.gridfs配置     #
storage.gridfshost = 127.0.0.1
storage.gridfsdbname = xyfs
storage.gridfsport = 27017
storage.gridfscollectionname = fs

# fastDFS 配置     #
storage.fastdfsconnecttimeout = 5
storage.fastdfsnetwork_timeout = 10
storage.fastdfscharset = UTF-8
storage.fastdfstrackerhttpport = 80
storage.fastdfsantistealtoken = no
storage.fastdfssecret_key = 1234567890
storage.fastdfstrackerserver = 192.168.17.112:22122

# SeaweedFS 配置     #
storage.seaweedfshost = localhost
storage.seaweedfsport = 9333
storage.seaweedfstimeout = 10

# ali OSS 配置     #
storage.aliendpoint = ""
storage.aliaccesskeyid  = ""
storage.aliaccesskeysecret = ""
storage.alibucketname  = ""
storage.alidownloadkey  = ""

# 第一次使用的配置：自动创建数据库表，如果是运行SQL脚本则无需使用此配置         #
# spring.jpa.hibernate.ddl-auto = create
# 第二次开始使用的配置：据库表会根据Entity的变动而更新         #
 spring.jpa.hibernate.ddl-auto = update 

# Sqlite数据源        #
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.datasource.url=jdbc:sqlite:DbSqlite/xyfs.db
spring.datasource.platform=sqlite
spring.jpa.database-platform= xy.FileSystem.Dialect.SQLiteDialect

# mysql数据源         #
#spring.datasource.url=jdbc:mysql://localhost:3306/xyfs
#spring.datasource.username=root
#spring.datasource.password=mysql

# oracle数据源         #
#spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
#spring.datasource.url=jdbc:oracle:thin:@127.0.0.1:1521:torcl
#spring.datasource.username=center
#spring.datasource.password=centerDB12345

# dbcp2连接池配置       #
spring.datasource.type=org.apache.commons.dbcp2.BasicDataSource
spring.datasource.dbcp2.max-wait-millis=10000
spring.datasource.dbcp2.min-idle=5
spring.datasource.dbcp2.initial-size=5
spring.datasource.dbcp2.validation-query=SELECT 1 FROM app
spring.datasource.dbcp2.connection-properties=characterEncoding=utf8

# druid连接池的配置信息       #
#spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
#spring.datasource.initialSize=5
#spring.datasource.minIdle=5
#spring.datasource.maxActive=20
#spring.datasource.maxWait=60000
#spring.datasource.timeBetweenEvictionRunsMillis=60000
#spring.datasource.minEvictableIdleTimeMillis=300000
#spring.datasource.validationQuery=SELECT 1 FROM App
#spring.datasource.testWhileIdle=true
#spring.datasource.testOnBorrow=false
#spring.datasource.testOnReturn=false
#spring.datasource.poolPreparedStatements=true
#spring.datasource.maxPoolPreparedStatementPerConnectionSize=20
#spring.datasource.filters=stat,wall,log4j

# spring jackson 时间配置         #
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.joda-date-time-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=GMT+8

# multipart 上传配置            #
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=1024MB
spring.servlet.multipart.max-request-size=1024MB

# oss 日志配置       #
logging.config=classpath:logback.xml
</code></pre>
