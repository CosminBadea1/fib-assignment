# Argyle API Task (Java) #4

## Build and run service locally with Docker

```
>> cd .\infrastructure\docker\
>> docker-compose up
```

After start up, app should be available at http://localhost:8000/fib?n=5

## Deploy to Kubernetes
```
>> cd .\infrastructure\k8s\
>> kubectl apply -f fib.yaml
```
After pod start up, app should be available at http://localhost:8000/fib?n=5

***IMPORTANT: Be aware that in order for the k8s autoscaling to work properly the cluster needs to have installed the metrics-server***
1. Download components.yaml from https://github.com/kubernetes-sigs/metrics-server/releases
2. Add the line --kubelet-insecure-tls under the args section of the file
3. kubectl apply -f components.yaml
