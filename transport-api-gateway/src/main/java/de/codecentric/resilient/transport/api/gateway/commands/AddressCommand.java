package de.codecentric.resilient.transport.api.gateway.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import de.codecentric.resilient.dto.AddressDTO;
import de.codecentric.resilient.dto.AddressResponseDTO;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class AddressCommand extends HystrixCommand<AddressResponseDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressCommand.class);

    private final AddressDTO addressDTO;

    private final RestTemplate restTemplate;

    private final boolean secondTry;

    public AddressCommand(AddressDTO addressDTO, RestTemplate restTemplate, boolean secondTry) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("AddressServiceClientGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("AddressServiceClient")));

        this.addressDTO = addressDTO;
        this.restTemplate = restTemplate;
        this.secondTry = secondTry;
    }

    @Override
    protected AddressResponseDTO run() throws Exception {
        // Calling remote rest Service via Service Discovery Eureka
        return restTemplate.postForObject("http://address-service/rest/address/validate", addressDTO, AddressResponseDTO.class);
    }

    @Override
    protected AddressResponseDTO getFallback() {

        if (secondTry) {
            LOGGER.debug(LOGGER.isDebugEnabled() ? "Second Address Service Call started" : null);
            // final second call
            AddressCommand addressCommand = new AddressCommand(addressDTO, restTemplate, false);
            return addressCommand.execute();

        } else {
            LOGGER.debug(LOGGER.isDebugEnabled() ? "Fallback Address Service call" : null);

            AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
            addressResponseDTO.setFallback(true);
            if (getFailedExecutionException() == null)
                addressResponseDTO.setErrorMsg("Error: unable to validate address");
            else
                addressResponseDTO
                    .setErrorMsg("Error: unable to validate address - " + getFailedExecutionException().getMessage());

            return addressResponseDTO;
        }
    }
}
