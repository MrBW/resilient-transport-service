package de.codecentric.resilient.booking.entity;

import javax.persistence.Embeddable;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Embeddable
public class Customer {

    private Long customerId;

    private String customerName;

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
