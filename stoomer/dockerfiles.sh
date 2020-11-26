#!/bin/sh

#
# mysql image/container def
docker build -t stoomerdb -f ./db.dockerfile .
docker run -d -p 3306:3306 --name stoomerdb --network="host" -e MYSQL_ROOT_PASSWORD=RootPassword -e MYSQL_DATABASE=stoomerdb -e MYSQL_USER=stoomer -e MYSQL_PASSWORD=StoomerSecrets stoomerdb

#
# tomcat image/container def
docker build -t stoomerapp -f ./app.dockerfile .
docker run -d -p 8080:8080 --name stoomerapp --network="host" stoomerapp

# docker container ls
# docker exec -it <?CONTAINER ID?> bash
# cd /usr/local/tomcat/logs
