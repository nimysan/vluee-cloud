## 模块说明 

> 本项目不含酒店业务逻辑，仅包含 用户/授权/网关等管理工具和服务

> [lombok与IDEA不兼容问题](https://youtrack.jetbrains.com/issue/IDEA-250718#focus=Comments-27-4418347.0-0)

### 技术栈


[Spring cloud alibaba](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/en-us/index.html#_introduction)

[Nacos](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)

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

#### AI店长服务说明和网关API访问路径 

|  注册的服务   | API访问路径  | 说明 |
|  ----  | ----  | ---- |
| common-api  | /common | 图片，附件上传等基础服务 |
| essync  | /search | ES索引建立，搜索 |
| message  | /notify | 短信，激光等各种通知 |
| revenue-center  | /revenue | 收益相关 |
| store-center  | /store | 门店相关 |
| task-center  | /task | 任务相关 |
| user-center  | /user | 用户相关 |

### 部署和启动

```bash
docker network create aistore-network

docker run -d -p 8701:8080 --name aistore-gateway --link nacos-standalone --network aistore-network aistore/gateway:latest
docker run -d -p 8702:8080 --name aistore-monitor --link nacos-standalone --network aistore-network aistore/monitor:latest

```