package de.codecentric.resilient.transport.api.gateway.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import de.codecentric.resilient.dto.CustomerResponseDTO;

/**
 * @author Benjamin Wilms
 */
public class CustommerCommand extends HystrixCommand<CustomerResponseDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustommerCommand.class);

    private final Long custommerId;

    private final RestTemplate restTemplate;

    private final boolean secondTry;

    public CustommerCommand(Long custommerId, RestTemplate restTemplate, boolean secondTry) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CustomerServiceClientGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("CustomerServiceClient")));
        this.custommerId = custommerId;
        this.restTemplate = restTemplate;

        this.secondTry = secondTry;
    }

    @Override
    protected CustomerResponseDTO run() throws Exception {
        return restTemplate.getForObject("http://customer-service/rest/customer/search/id?customerId={customerId}",
            CustomerResponseDTO.class, custommerId);
    }

    @Override
    protected CustomerResponseDTO getFallback() {

        if (secondTry) {
            LOGGER.debug(LOGGER.isDebugEnabled() ? "Second Customer Service Call started" : null);
            // final second call
            return new CustommerCommand(custommerId, restTemplate, false).execute();
        } else {

            CustomerResponseDTO customerReqResponseDTO = new CustomerResponseDTO();
            customerReqResponseDTO.setFallback(true);
            if (getFailedExecutionException() == null)
                customerReqResponseDTO.setErrorMsg("Error: unable to get customer");
            else
                customerReqResponseDTO
                    .setErrorMsg("Error: unable to get customer - " + getFailedExecutionException().getMessage());

            return customerReqResponseDTO;
        }
    }
}
