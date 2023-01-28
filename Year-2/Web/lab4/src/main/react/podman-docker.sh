#!/bin/sh

podman pod create --name postgre-sql -p 9876:80

podman run --pod postgre-sql \
-e 'PGADMIN_DEFAULT_EMAIL=roman.vassilchenko.work@gmail.com' \
-e 'PGADMIN_DEFAULT_PASSWORD=postgrespw'  \
--name pgadmin \
 -d docker.io/dpage/pgadmin4:latest

podman run --name db --pod=postgre-sql -d \
   -e POSTGRES_USER=postgres \
   -e POSTGRES_PASSWORD=postgrespw \
   docker.io/library/postgres:14



#To pause and unpause the pod use:
#
#podman pod pause postgre-sql
#podman pod unpause postgre-sql
#Start and stop the pod with the command.
#
#podman pod stop postgre-sql
#podman pod start postgre-sql
#In case you want to remove the container, use the command:
#
#podman pod rm postgre-sql