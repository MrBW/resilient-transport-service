package de.codecentric.resilient.transport.api.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.codecentric.resilient.dto.BookingServiceResponseDTO;

/**
 * @author Benjamin Wilms (
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransportApiGatewayResponse extends BookingServiceResponseDTO {
}
