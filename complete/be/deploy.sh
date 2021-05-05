mvn clean package

docker image rm demoasset-be-complete
docker build -t demoasset-be-complete .

kubectl delete deployment demoasset-be -n demoasset

#minikube cache delete demoasset-be-complete:latest
minikube cache add demoasset-be-complete:latest

kubectl apply -f deployment/k8s/deployment.yaml 

kubectl apply -f deployment/k8s/service.yaml 