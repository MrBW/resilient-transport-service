package com.codecentric.de.resilient.dto;

/**
 * @author Benjamin Wilms
 */
public class CustomerDTO extends AbstractDTO {

    public Long customerId;

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
}
