#!/bin/bash

curl localhost:8083/removeAllAgent
curl localhost:8083/removeAllOrder

kubectl delete -n default deployment abhidelivery
kubectl delete -n default deployment abhirestaurant
kubectl delete -n default deployment abhiwallet
kubectl delete -n default deployment abhidatabase

kubectl delete -n default service abhidelivery
kubectl delete -n default service abhirestaurant
kubectl delete -n default service abhiwallet
kubectl delete -n default service abhidatabase

eval $(minikube docker-env)
docker image prune -a
