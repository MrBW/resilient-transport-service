package de.codecentric.resilient.booking.entity;

import javax.persistence.*;

import org.joda.time.LocalDateTime;

/**
 * @author Benjamin Wilms
 */
@Entity
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Customer customer;

    private Long connote;

    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street",column=@Column(name="receiverStreet")),
            @AttributeOverride(name="city",column=@Column(name="receiverCity")),
            @AttributeOverride(name="postcode",column=@Column(name="receiverPostcode")),
            @AttributeOverride(name="country",column=@Column(name="receiverCountry")),
            @AttributeOverride(name="streetNumber",column=@Column(name="receiverStreeNumber"))
    })
    private Address receiverAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street",column=@Column(name="senderStreet")),
            @AttributeOverride(name="city",column=@Column(name="senderCity")),
            @AttributeOverride(name="postcode",column=@Column(name="senderPostcode")),
            @AttributeOverride(name="country",column=@Column(name="senderCountry")),
            @AttributeOverride(name="streetNumber",column=@Column(name="senderStreeNumber"))
            })
    private Address senderAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getConnote() {
        return connote;
    }

    public void setConnote(Long connote) {
        this.connote = connote;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Address getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(Address receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Address getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(Address senderAddress) {
        this.senderAddress = senderAddress;
    }
}
