#!/bin/bash
clear
curl -H "Content-Type: application/json" --data @./json-payload/booking-req.json http://localhost:8081/rest/transport/booking/simulate?ms=360000
echo
