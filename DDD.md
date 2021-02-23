# DDD

### DDD lite 简要实施原则
DDD unique验证方法， 如何做唯一性验证有很多种办法， 当前选择将唯一性验证做在domain service中 [DDD验证唯一性](https://github.com/ardalis/DDD-NoDuplicates)
所谓唯一性都必须有个前提条件:Scope, 角色名称的唯一性存在两个维度- System(整个系统级别)
和Tenant租户级别 [采用System作为聚合根来验证唯一性](https://stackoverflow.com/questions/2916899/how-to-handle-set-based-consistency-validation-in-cqrs)
聚合内对象引用，聚合外Id应用
