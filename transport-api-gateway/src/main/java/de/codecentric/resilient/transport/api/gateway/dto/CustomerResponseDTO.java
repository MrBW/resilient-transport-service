package de.codecentric.resilient.transport.api.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.codecentric.resilient.dto.FallbackAbstractDTO;

/**
 * @author Benjamin Wilms
 */

public class CustomerResponseDTO extends FallbackAbstractDTO {

    @JsonProperty(required = true)
    public Long customerId;

    @JsonProperty(required = true)
    public String customerName;

    public CustomerResponseDTO(Long customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public CustomerResponseDTO() {

    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerDTO{");
        sb.append("customerId=").append(customerId);
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
