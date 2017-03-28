var request = require("request");
var moment = require('moment');
var startTime = moment();

// request
var jsonRequest = {
  "customerId": 1,
  "sender-country": "de",
  "sender-city": "Solingen",
  "sender-postcode": "42697",
  "sender-street": "Hochstraße",
  "sender-streetnumber": "11",
  "receiver-country": "de",
  "receiver-city": "Solingen",
  "receiver-postcode": "42697",
  "receiver-street": "Hochstraße",
  "receiver-streetnumber": "11"
};

var options = {
  uri: 'http://localhost:8081/rest/transport/booking/simulate?ms=180000',
  method: 'POST',
  json: jsonRequest
};

request(options, function(error, response, body) {
  console.log(body);
  console.log("duration: " + (moment() - startTime) + " ms");
});
