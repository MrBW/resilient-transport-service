package de.codecentric.resilient.transport.api.gateway.commands;

import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import de.codecentric.resilient.transport.api.gateway.dto.CustomerResponseDTO;

/**
 * @author Benjamin Wilms
 */
public class CustommerCommand extends HystrixCommand<CustomerResponseDTO> {

    private final Long custommerId;

    private final RestTemplate restTemplate;

    public CustommerCommand(Long custommerId, RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CustomerServiceClientGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("CustomerServiceClient")));
        this.custommerId = custommerId;
        this.restTemplate = restTemplate;

    }

    @Override
    protected CustomerResponseDTO run() throws Exception {
        return restTemplate.getForObject("http://customer-service/rest/customer/search/id?customerId={customerId}",
            CustomerResponseDTO.class, custommerId);
    }

    @Override
    protected CustomerResponseDTO getFallback() {

        CustomerResponseDTO customerReqResponseDTO = new CustomerResponseDTO();
        customerReqResponseDTO.setFallback(true);
        if (getFailedExecutionException() == null)
            customerReqResponseDTO.setErrorMsg("Error: unable to get customer");
        else
            customerReqResponseDTO.setErrorMsg("Error: unable to get customer - " + getFailedExecutionException().getMessage());

        return customerReqResponseDTO;
    }
}
