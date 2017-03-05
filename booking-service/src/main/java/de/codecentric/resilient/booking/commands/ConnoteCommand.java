package de.codecentric.resilient.booking.commands;

import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import de.codecentric.resilient.dto.ConnoteDTO;

/**
 * @author Benjamin Wilms
 */
public class ConnoteCommand extends HystrixCommand<ConnoteDTO> {

    private final RestTemplate restTemplate;

    public ConnoteCommand(RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ConnoteServiceClientGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("ConnoteServiceClient")));
        this.restTemplate = restTemplate;
    }

    @Override
    protected ConnoteDTO run() throws Exception {
        return restTemplate.getForObject("http://connote-service/rest/connote/create", ConnoteDTO.class);
    }

    @Override
    protected ConnoteDTO getFallback() {
        ConnoteDTO connoteDTO = new ConnoteDTO();
        connoteDTO.setFallback(true);
        if (getFailedExecutionException() != null)
            connoteDTO.setErrorMsg("Error: unable to create connote - " + getFailedExecutionException().getMessage());
        else
            connoteDTO.setErrorMsg("Error: unable to create connote");
        return connoteDTO;
    }
}
