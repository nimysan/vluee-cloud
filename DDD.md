# DDD

### DDD lite 简要实施原则

1. DDD unique验证方法， 如何做唯一性验证有很多种办法， 当前选择将唯一性验证做在domain service中 [DDD验证唯一性](https://github.com/ardalis/DDD-NoDuplicates)
   所谓唯一性都必须有个前提条件:Scope, 角色名称的唯一性存在两个维度- System(整个系统级别)
2.

和Tenant租户级别 [采用System作为聚合根来验证唯一性](https://stackoverflow.com/questions/2916899/how-to-handle-set-based-consistency-validation-in-cqrs)

3. 聚合内对象引用，聚合外Id应用
4. 查询操作独立建模（基本上就认为是采用简单得脚本实现方式), 名字不建议用Repository，而是直接用Finder。 里面直接写查询SQL语句。

5. 子域是问题域，限届上下文是解决方案域，子域的问题通过限界上下文解决，就是这个问题的答案.

### 模式

1 反面模式触目惊心 [参见](https://zh.wikipedia.org/wiki/%E5%8F%8D%E9%9D%A2%E6%A8%A1%E5%BC%8F)