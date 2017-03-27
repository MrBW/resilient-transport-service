package de.codecentric.resilient.booking.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import de.codecentric.resilient.dto.ConnoteDTO;

/**
 * @author Benjamin Wilms
 */
public class ConnoteCommand extends HystrixCommand<ConnoteDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnoteCommand.class);

    private final RestTemplate restTemplate;

    private final boolean secondTry;

    public ConnoteCommand(RestTemplate restTemplate, boolean secondTry) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ConnoteServiceClientGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("ConnoteServiceClient")));
        this.restTemplate = restTemplate;
        this.secondTry = secondTry;
    }

    @Override
    protected ConnoteDTO run() throws Exception {
        return restTemplate.getForObject("http://connote-service/rest/connote/create", ConnoteDTO.class);
    }

    @Override
    protected ConnoteDTO getFallback() {

        if (secondTry) {
            LOGGER.debug(LOGGER.isDebugEnabled() ? "Second Connote Service Call started" : null);

            ConnoteCommand connoteCommand = new ConnoteCommand(restTemplate, false);
            return connoteCommand.execute();

        } else {

            LOGGER.debug(LOGGER.isDebugEnabled() ? "Fallback Connote Service call" : null);

            ConnoteDTO connoteDTO = new ConnoteDTO();
            connoteDTO.setFallback(true);

            if (getExecutionException() != null) {
                Exception exceptionFromThrowable = getExceptionFromThrowable(getExecutionException());
                String errorMessage = (exceptionFromThrowable != null) ? exceptionFromThrowable.getMessage() : "";
                connoteDTO.setErrorMsg(errorMessage);
            } else
                connoteDTO.setErrorMsg("unable to create connote");
            return connoteDTO;
        }
    }
}