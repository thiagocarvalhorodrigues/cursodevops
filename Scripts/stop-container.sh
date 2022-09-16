#!/bin/bash

docker rm -f $(docker ps -qa) || true
docker rmi he220652/app-dcw5:develop || true
