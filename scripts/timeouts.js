var request = require("request");
var baseUrl = "http://0.0.0.0:2379/v2/keys/hystrix";

request({
    url: baseUrl + '/hystrix.command.ConnoteServiceClient.execution.isolation.thread.timeoutInMilliseconds',
    method: 'PUT',
    qs: {value: "300"},
}, function(error, response, body){
    console.log(response.statusCode, body);
});
