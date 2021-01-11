# 用户和UAMS (统一授权中心)

> [Django PRBAC文档](https://django-prbac.readthedocs.io/en/latest/tutorial.html)

> documents/Abdallah-Khayat2005_Chapter_AFormalModelForParameterizedRo.pdf PRBAC说明

> [垂直权限(功能权限)-水平权限(数据权限)](https://cloud.tencent.com/developer/article/1099266)

### 需要解决的问题

1. 店长自动拥有店总权限 (店长自动进入店总用户组)
2. 一个用户有多个工作属性。 一个用户是A店的投资人，也可能是B店，C店的店长。 角色绑定数据权限
3. 品牌管理员希望自己有不同的角色管理行为 --- 这个给角色增加标签来解决？
4. 一个用户有多个工作属性导致的权限冲突 (采用合并机制) - 合并机制如何确保数据权限?

### 设计原则

> 角色/权限/数据权限 不依赖外部组织机构，必须能够内部逻辑自洽

1. 支持多租户
2. RBAC2模型(用户组/角色继承/权限分类)
3. 支持区域管理 (区域隔离， 通过数据权限来实现) - TODO
4. 用户/用户组/角色 支持品牌隔离
5. 功能权限通过RBAC来解决
6. 数据权限自定义

#### 数据权限的问题

1. 张三是A，B，C三家店的店总，同时是 粤东区域的区总， A属于粤西区域， C属于粤东区域

--resource: ums:task:submit(完成任务)

--resource: ums:task:list(列出任务)

--resource: ums:task:cancel(取消任务)

--张三： 店总角色/区总角色

1. 查看门店任务 （需要找出 A，B，C 三家店), 


2. 

### 问题
1. 如何管理一个用户拥有所有品牌?






