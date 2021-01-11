# 技术连接

###

[docker-maven-plugin](http://dmp.fabric8.io/#global-configuration)

### 如何调试service网络

1. 首先确保创建得网络带上 -attachable 参数，否则独立得container无法连接上网络

```
docker create network -d overlay --attachable as-dev-swarm
```

2. 启动一个带有网络工具得container 然后连上之前创建得网络
```
docker run --name ntool dolphm/network-tools /bin/bash
docker network connect as-dev-swarm #container-id#
```