package com.codecentric.de.resilient.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Benjamin Wilms
 */
public class BookingServiceResponseDTO extends AbstractDTO {

    @JsonProperty(required = true)
    private ConnoteDTO connoteDTO;

    @JsonProperty(required = true)
    private Date created;

    @JsonProperty(required = true)
    private CustomerDTO customerDTO;

    public ConnoteDTO getConnoteDTO() {
        return connoteDTO;
    }

    public void setConnoteDTO(ConnoteDTO connoteDTO) {
        this.connoteDTO = connoteDTO;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
