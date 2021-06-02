## 模块说明

### 典型场景：

1. 维护门店信息， 门店拥有品牌。 门店有相关员工和店长， 系统根据规则分发任务给到门店。
2. 任务创建后，实时统计任务数量
3. 任务创建后，实时纳入搜索引擎

> 本项目不含酒店业务逻辑，仅包含 用户/授权/网关等管理工具和服务

> [lombok与IDEA不兼容问题](https://youtrack.jetbrains.com/issue/IDEA-250718#focus=Comments-27-4418347.0-0)

### 技术栈

[Spring cloud alibaba](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/en-us/index.html#_introduction)

[Nacos](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)

[PlantUML文档工具](https://plantuml.com/zh/activity-diagram-beta)

```
File | Settings | Build, Execution, Deployment | Compiler | Build process VM options 
添加：
-Djps.track.ap.dependencies=false

```

### 模块说明

``` lua
vluee-cloud
├── gateway -- 基于Spring cloud gateway的网关，依赖nacos做服务发现
├── monitor -- 基于Spring-Boot-Admin的管理程序，依赖nacos做服务发现
├── aistore-user -- （废弃中，预计并入uams服务）用户中心，提供用户信息管理
├── uams --  统一授权管理中心 用户/角色/资源/权限统一管理
├── common -- 非服务，提供常量，名词，工具方法等
├── tenant-- 租户服务，提供租户管理，租户品牌管理服务
├── authentications -- 基于Spring Security and OAuth2实现的独立用户认证中心
```

### spring boot admin

用户名和密码访问设置请参考： application.yml, 默认为 jzboss/jzboss123

### aistore-gateway

集成knife4j swagger文档： http://localhost:8201/doc.html

参考: [cloud gateway与knife4j集成](https://blog.csdn.net/u010192145/article/details/100152984)

### AI店长服务说明和网关API访问路径

|  注册的服务   | API访问路径  | 说明 |
|  ----  | ----  | ---- |
| commons  | /common | 图片，附件上传等基础服务 |
| auth | /search | ES索引建立，搜索 |
| gateway  | /gateway | 短信，激光等各种通知 |
| monitor  | /monitor | 收益相关 |
| organizations  | /orgs | 组织机构，门店相关 |
| statistics  | /statistics | 数据统计 |
| tenants  | /user | 租户管理相关 |
| uams  | /uams | 用户信息相关 |
| uams  | /uams | 用户信息相关 |
| uams  | /uams | 用户信息相关 |

### 部署和启动

|  路径   | 说明 |
|  ----   | ---- |
| deploy  | 部署支持 |
| doc  | 设计文档等 |


### 前端快速开发方案 ### 

https://www.erupt.xyz/#!/

### 待解决的问题 ###

1. Kafka作为Event Dispatcher的标准实现方式
2. 分布式锁的实现，暂时内置了基于本地数据库的分布式锁，需要实现基于Redis\Nacos\Zookeeper的方案
3. 前端实现方案 - 暂时想基于Angular的实现方案 （工程管理、编码规范优势）
4. k8s部署方案 （部署和监控、服务治理相关）
5. 数据Schema的初始化管理方式、升级管理方案 （在DevOps 集成开发形式)
6. Spring Integration/Spring BPM的相关的引入和集成场景
7. 独立的搜索服务场景(Searches 基于ES的独立搜索服务提供)
8. Module生成(带架构和基本包的)maven定制插件