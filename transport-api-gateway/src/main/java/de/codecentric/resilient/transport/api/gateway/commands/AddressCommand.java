package de.codecentric.resilient.transport.api.gateway.commands;

import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import de.codecentric.resilient.dto.AddressDTO;
import de.codecentric.resilient.dto.AddressResponseDTO;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class AddressCommand extends HystrixCommand<AddressResponseDTO> {

    private final AddressDTO addressDTO;

    private final RestTemplate restTemplate;

    public AddressCommand(AddressDTO addressDTO, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("AddressServiceClientGroup"));

        this.addressDTO = addressDTO;
        this.restTemplate = restTemplate;
    }

    @Override
    protected AddressResponseDTO run() throws Exception {
        // Calling remote rest Service via Service Discovery Eureka
        return restTemplate.postForObject("http://address-service/rest/address/validate", addressDTO, AddressResponseDTO.class);
    }

    @Override
    protected AddressResponseDTO getFallback() {
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setFallback(true);
        addressResponseDTO.setErrorMsg("Error:" + getFailedExecutionException().getMessage());

        return addressResponseDTO;
    }
}
