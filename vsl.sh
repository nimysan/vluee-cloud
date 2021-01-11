#!/bin/bash
#Filename vsl.sh
# View service log
serviceName=$1
echo "Will tail log for ${serviceName}"
docker service logs -f $(docker service ls | grep ${serviceName} | grep -v grep | awk '{print $1}')
