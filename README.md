# resilient-transport-service
Resilient demo application - Transport Service


![alt text](https://github.com/MrBW/resilient-transport-service/blob/master/docu/HystrixTimeoutDefaults.png "Overview")

Slides from my talk @ JavaLand 2017 can be found at speakerdeck.com

<script async class="speakerdeck-embed" data-id="352b2f05babe495e82e1cc240f896837" data-ratio="1.29456384323641" src="//speakerdeck.com/assets/embed.js"\>
</script\>

# How to run
## Requirements
You have to install and run Docker for Mac or Windows on your system. By running mvn package, spotify maven plugin will create all Docker containers.

Docker for Mac:
https://docs.docker.com/docker-for-mac/install/

Docker for Windows:
https://docs.docker.com/docker-for-windows/install/



## Steps
- mvn clean package
- docker-compose up etcd
- run etcd-init.sh script from bash
- docker-compose stop
- docker-compose up

Demo is running, try to send booking request...

## Booking request
Try to send a booking request, you will find a sample nodes.js script under:
https://github.com/MrBW/resilient-transport-service/blob/master/scripts/booking-create.js

...more details are coming...
