# shareNotes

` 微信小程序 ` `后端基于springBoot`

## 项目介绍

`shareNotes` 小程序可以让朋友之间轻松的共享公共的笔记，其笔记输入可以是纯文本，也可以是markdown或者html格式。
并且添加了由Netty+WebSocket实现的IM，实现即使交流的功能

## 项目实例
<img src="https://img-blog.csdnimg.cn/2019102209481690.JPG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNzU3MTc5NQ==,size_16,color_FFFFFF,t_70" width = "200" height = "400" alt="图片名称">

<img src="https://img-blog.csdnimg.cn/20191022094505884.JPG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNzU3MTc5NQ==,size_16,color_FFFFFF,t_70" width = "200" height = "400" alt="图片名称">

<img src="https://img-blog.csdnimg.cn/20191022094716537.JPG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNzU3MTc5NQ==,size_16,color_FFFFFF,t_70" width = "200" height = "400" alt="图片名称">

<img src="https://img-blog.csdnimg.cn/20191022094830934.JPG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNzU3MTc5NQ==,size_16,color_FFFFFF,t_70" width = "200" height = "400" alt="图片名称">

因为此小程序属于记事服务，属于企业级小程序。所以只有体验版
欢迎来体验呀。

<img src="https://img-blog.csdnimg.cn/20200229190640537.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNzU3MTc5NQ==,size_16,color_FFFFFF,t_70" width = "200" height = "200" alt="图片名称">




## 项目结构
``` 
shareNotes
├── shareNotes-core -- 工具类及通用代码
├── shareNotes-db -- 数据库操作代码
├── shareNotes-wx-api -- 微信小程序系统接口
└── shareNotes -- 小程序前端
```

### 项目

## 技术选型

#### 后端技术

技术 | 说明 | 
----|----|
Spring Boot | 容器+MVC框架 |
MyBatis | ORM框架  | 
MyBatisGenerator | 数据层代码生成 | 
Swagger-UI | 文档生产工具 | 
ActiveMq | 消息队列 | 
Redis | 分布式缓存 | 
Docker | 应用容器引擎 | 
ElasticSearch | 基于Lucene的搜索服务器 | 
Druid | 数据库连接池 | 
AOP | 面向切面编程 |
JWT | JWT登录支持 | 
Lombok | 简化对象封装工具 | 
Jackson | 封装json工具 | 
binarywang | 封装wxApi工具 | 


#### 前端技术

技术 | 说明 | 
----|----|
微信小程序 | 微信小程序 |
Towxml | 解析html到wxml工具 | 
iView | UI组件库 | 

## 项目环境
- 安装 JDK（1.8+）
- 安装 Maven (3.3.0+)
- 安装 Redis服务 (3.0+)
- 安装 MySQL (5.7+)
- 安装 ActivetMQ （5.15.9） 其版本和JDK对应
- 安装 微信开发者工具
- 安装 ElasticSearch (6.3.2+) 需要添加上分词器

## 配置
### 数据库
share-Note-db模块下的 sql --- shareNotes-table.sql
并且在 application-db.properties 中配置数据库 
### 存储配置
share-Note-db模块下的 application-core.properties 配置自己的路径 如果为本地调试 可以使用
address: http://localhost:8088/wx/storage/fetch/ 
服务器同理，这里已经配置好了sso，可以照对应添加

### 项目技术人员
- kiwi
- hu
- shao