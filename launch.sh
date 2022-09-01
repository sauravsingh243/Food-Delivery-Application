#!/bin/bash
minikube start --extra-config=kubelet.housekeeping-interval=5s
eval $(minikube docker-env)

cd database/
./mvnw -DskipTests package
docker build -t abhidatabase .
kubectl apply -f database.yaml
sleep 8
kubectl expose deployment abhidatabase --type=LoadBalancer --port=8080
sleep 10
kubectl port-forward deployment/abhidatabase 8083:8080 &

cd ..
cd Restaurant/
./mvnw -DskipTests package
docker build -t abhirestaurant .
kubectl apply -f restaurant.yaml
sleep 8
kubectl expose deployment abhirestaurant --type=LoadBalancer --port=8080
sleep 10
kubectl port-forward deployment/abhirestaurant 8080:8080 &

cd ..
cd Wallet/
./mvnw -DskipTests package
docker build -t abhiwallet .
kubectl apply -f wallet.yaml
sleep 8
kubectl expose deployment abhiwallet --type=LoadBalancer --port=8080
sleep 10
kubectl port-forward deployment/abhiwallet 8082:8080 &


cd ..
cd Delivery/
./mvnw -DskipTests package
docker build -t abhidelivery .
kubectl apply -f delivery.yaml
sleep 8
kubectl expose deployment abhidelivery --type=LoadBalancer --port=8080
sleep 10
kubectl port-forward deployment/abhidelivery 8081:8080 &

minikube addons enable metrics-server
kubectl autoscale deployment abhidelivery --cpu-percent=50 --min=1 --max=3




