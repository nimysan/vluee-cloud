#!/bin/bash
#Filename swarm.sh
serviceName=$1

as-build() {
  mvn -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true -DskipTests=true clean package k8s:build -pl vluee-docker-maven-plugin,aistore-common,aistore-${serviceName}
}

as-build
