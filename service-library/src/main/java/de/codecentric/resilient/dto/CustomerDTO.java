package de.codecentric.resilient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Benjamin Wilms
 */

public class CustomerDTO extends AbstractDTO {

    @JsonProperty(required = true)
    public Long customerId;

    @JsonProperty(required = true)
    public String customerName;

    public CustomerDTO(Long customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public CustomerDTO() {

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
