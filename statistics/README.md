# 统计和分析(实时)

### 事件统计

1. 统计整体事件量 (监听事件，并加总)
2. 统计每个事件的量

### 任务统计

### 技术基础
1. 时序数据库 InfluxDB
2. 创建库

```
curl -i -XPOST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE mydb"
```

InfluxDB特征：

无结构(无模式)：可以是任意数量的列
可以设置metric的保存时间
支持与时间有关的相关函数(如min、max、sum、count、mean、median等)，方便统计
支持存储策略:可以用于数据的删改。(influxDB没有提供数据的删除与修改方法)
支持连续查询:是数据库中自动定时启动的一组语句，和存储策略搭配可以降低InfluxDB的系统占用量。
原生的HTTP支持，内置HTTP API
支持类似sql语法
支持设置数据在集群中的副本数
支持定期采样数据，写入另外的measurement，方便分粒度存储数据。
自带web管理界面，方便使用(登入方式：http://< InfluxDB-IP >:8083)