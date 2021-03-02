# DDD

### DDD lite 简要实施原则

1. DDD unique验证方法， 如何做唯一性验证有很多种办法， 当前选择将唯一性验证做在domain service中 [DDD验证唯一性](https://github.com/ardalis/DDD-NoDuplicates)
   所谓唯一性都必须有个前提条件:Scope, 角色名称的唯一性存在两个维度- System(整个系统级别)
2. 和Tenant租户级别 [采用System作为聚合根来验证唯一性](https://stackoverflow.com/questions/2916899/how-to-handle-set-based-consistency-validation-in-cqrs)

3. 聚合内对象引用，聚合外Id应用
4. 查询操作独立建模（基本上就认为是采用简单得脚本实现方式), 名字不建议用Repository，而是直接用Finder。 里面直接写查询SQL语句。 更复杂的可以考虑CQRS模式(一种读写分离的方法)

5. 子域是问题域，限届上下文是解决方案域，子域的问题通过限界上下文解决，就是这个问题的答案.

### 模式

1 反面模式触目惊心 [参见](https://zh.wikipedia.org/wiki/%E5%8F%8D%E9%9D%A2%E6%A8%A1%E5%BC%8F)

### 事件处理实践 

1 领域事件需被存储起来

2 事件处理器需具备幂等性(为了保持幂等性，发布的事件需具备唯一ID)。 领域数据存储和发布事件要求强一致(如果底层采用关系型数据库，则需在一个事务内)。

3 领域事件存入数据库后(状态为 ‘未发布’)，立即触发做真实发出操作（真实发布依托发布机制，如SpringEvent或者Kafka等消息机制），事件发出成功后，标记事件为 ‘已发布’。 
事件消费者是否成功接收事件并作为“业务处理”，都由事件消费方与事件发布基础设施去保证和处理。 事件库不在承担此职责。
