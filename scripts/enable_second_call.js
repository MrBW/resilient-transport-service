var request = require("request");
var baseUrl = "http://0.0.0.0:2379/v2/keys/hystrix";
var data = {value: "true"};

request({
    url: baseUrl + '/second.service.call',
    method: 'PUT',
    qs: data,
}, function(error, response, body){
    if(error) {
        console.log(error);
    } else {
        console.log(response.statusCode, body);
    }
});
