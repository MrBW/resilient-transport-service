# resilient-transport-service
Resilient demo application - Transport Service


![alt text](https://github.com/MrBW/resilient-transport-service/blob/master/docu/HystrixTimeoutDefaults.png "Overview")

# How to run

## Steps
- mvn clean package
- docker-compose up etcd
- run etcd-init.sh script from bash
- docker-compose stop
- docker-compose up

Demo is running, try to send booking request...

More info are coming soon 