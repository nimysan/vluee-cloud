
### Rabbit MQ ###

1. 下载erlang和Rabbit MQ安装包,分别安装
2. rabbitmq_plugins enable rabbitmq_management
3. 设置用户

```bash
rabbitmqctl add_user admin admin  //添加用户，后面两个参数分别是用户名和密码
rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"  //添加权限
rabbitmqctl set_user_tags username administrator  //修改用户角色,将用户设为管理员
```

[RabbitMQ管理地址](http://localhost:15672/#/) 用户名： admin/admin