#!/bin/bash
#Filename swarm.sh
serviceName=$1

echo "Will reinstall service ${serviceName}"

as-uninstall() {
  # check service exist or not
  dockerServiceID=$(docker service ls | grep ${serviceName} | grep -v grep | awk '{print $1}')
  echo "Docker service id is ${dockerServiceID}"
  if [ -z "${dockerServiceID}" ]; then
    echo "service does not exits"
  else
    echo "Service exist as id ${dockerServiceID} and will be removed"
    docker service rm "${dockerServiceID}"
    echo "Service is deleted"
  fi
}

as-build() {
  mvn -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true -DskipTests=true clean package docker:build vluee-docker:generate -pl aistore-${serviceName}
}

as-install() {
  docker stack deploy -c aistore-${serviceName}/target/aistore-${serviceName}-service.yaml as
}
# step 1: delete service
as-uninstall
# step 2: build latest image
as-build
# step 3: create new service
as-install
