#!/usr/bin/env bash

baseurl=http://localhost:2379/v2/keys/hystrix

# curl -L -X PUT http://localhost:2379/v2/keys/hystrix/chaos.monkey.active -d value="true"

echo --- remove hystrix keys from etcd server ---
curl -L -X DELETE $baseurl?recursive=true

# Chaos Monkey
curl -L -X PUT $baseurl/chaos.monkey.active -d value="false"
curl -L -X PUT $baseurl/chaos.monkey.timeout -d value="3000"

# Transport Service Hystrix Timeouts
curl -L -X PUT $baseurl/hystrix.command.AddressServiceClient.execution.isolation.thread.timeoutInMilliseconds -d value="1000"
curl -L -X PUT $baseurl/hystrix.command.BookingServiceClient.execution.isolation.thread.timeoutInMilliseconds -d value="1000"
curl -L -X PUT $baseurl/hystrix.command.CustomerServiceClient.execution.isolation.thread.timeoutInMilliseconds -d value="1000"
curl -L -X PUT $baseurl/hystrix.command.ConnoteServiceClient.execution.isolation.thread.timeoutInMilliseconds -d value="1000"

# Fallbacks enabled
curl -L -X PUT $baseurl/hystrix.command.AddressServiceClient.fallback.enabled -d value="true"
curl -L -X PUT $baseurl/hystrix.command.BookingServiceClient.fallback.enabled -d value="true"
curl -L -X PUT $baseurl/hystrix.command.CustomerServiceClient.fallback.enabled -d value="true"
curl -L -X PUT $baseurl/hystrix.command.ConnoteServiceClient.fallback.enabled -d value="true"

echo --- ETCD INIT DONE ---