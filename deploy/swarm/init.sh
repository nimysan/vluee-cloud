#!/bin/bash
#docker swarm mode
docker network create -d overlay as-dev-swarm

#Goto deploy/swarm
docker stack deploy -c redis.yaml as
docker stack deploy -c as-mysql.yaml as