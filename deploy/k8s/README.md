### k8s ###

### K8S cluster infromation
```bash
kubectl version
kubectl get nodes
```

#### 安装 nacos [nacos k8s部署](https://nacos.io/zh-cn/docs/use-nacos-with-kubernetes.html)

解决github下载不了的问题
```bash

git clone https://gitee.com/kennylee/nacos-k8s.git

# 修改当前context的默认workspace
kubectl config set-context  --namespace=dev --current
```

### K8S集群 NFS/GFS 说明
