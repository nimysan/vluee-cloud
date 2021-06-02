# 安装说明和指令

```bash
docker network create aistore-network

docker run -d -p 8701:8080 --name aistore-gateway --link nacos-standalone --network aistore-network aistore/gateway:latest
docker run -d -p 8702:8080 --name aistore-monitor --link nacos-standalone --network aistore-network aistore/monitor:latest


docker service create --name portainer_agent --network as-dev-swarm --publish mode=host,target=9001,published=9001 -e AGENT_CLUSTER_ADDR=tasks.portainer_agent --mode global --mount type=bind,src=//var/run/docker.sock,dst=/var/run/docker.sock --mount type=bind,src=//var/lib/docker/volumes,dst=/var/lib/docker/volumes --mount type=bind,src=/,dst=/host portainer/agent
```

## 部署 zookeeper集群

docker stack deploy -c ./swarm/zookeeper.yaml zc

## 部署kafka集群

docker stack deploy -c kafka.yaml kc

[kafka_manager](http://localhost:9093/)