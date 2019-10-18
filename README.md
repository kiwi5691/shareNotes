# shareNotes

` 微信小程序 ` `后端基于springBoot`

## 项目介绍

`shareNotes` 小程序可以让朋友之间轻松的共享公共的笔记，其笔记输入可以是纯文本，也可以是markdown或者html格式。
## 项目结构
``` 
shareNotes
├── shareNotes-core -- 工具类及通用代码
├── shareNotes-db -- 数据库操作代码
├── shareNotes-wx-api -- 微信小程序系统接口
└── shareNotes -- 小程序前端
```


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


### 项目技术人员
- kiwi
- hu
- shao