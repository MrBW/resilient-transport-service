package com.codecentric.de.resilient.transport.api.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class BookingRequestDTO {

    @JsonProperty(required = true)
    private Long customerId;

    @JsonProperty(required = true, value = "sender-country")
    private String senderCountry;

    @JsonProperty(required = true, value = "sender-city")
    private String senderCity;

    @JsonProperty(required = true, value = "sender-postcode")
    private String senderPostcode;

    @JsonProperty(required = true, value = "sender-street")
    private String senderStreet;

    @JsonProperty(required = true, value = "sender-streetnumber")
    private String senderStreetNumber;

    @JsonProperty(required = true, value = "receiver-country")
    private String receiverCountry;

    @JsonProperty(required = true, value = "receiver-city")
    private String receiverCity;

    @JsonProperty(required = true, value = "receiver-postcode")
    private String receiverPostcode;

    @JsonProperty(required = true, value = "receiver-street")
    private String receiverStreet;

    @JsonProperty(required = true, value = "receiver-streetnumber")
    private String receiverStreetNumber;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderPostcode() {
        return senderPostcode;
    }

    public void setSenderPostcode(String senderPostcode) {
        this.senderPostcode = senderPostcode;
    }

    public String getSenderStreet() {
        return senderStreet;
    }

    public void setSenderStreet(String senderStreet) {
        this.senderStreet = senderStreet;
    }

    public String getSenderStreetNumber() {
        return senderStreetNumber;
    }

    public void setSenderStreetNumber(String senderStreetNumber) {
        this.senderStreetNumber = senderStreetNumber;
    }

    public String getReceiverCountry() {
        return receiverCountry;
    }

    public void setReceiverCountry(String receiverCountry) {
        this.receiverCountry = receiverCountry;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverPostcode() {
        return receiverPostcode;
    }

    public void setReceiverPostcode(String receiverPostcode) {
        this.receiverPostcode = receiverPostcode;
    }

    public String getReceiverStreet() {
        return receiverStreet;
    }

    public void setReceiverStreet(String receiverStreet) {
        this.receiverStreet = receiverStreet;
    }

    public String getReceiverStreetNumber() {
        return receiverStreetNumber;
    }

    public void setReceiverStreetNumber(String receiverStreetNumber) {
        this.receiverStreetNumber = receiverStreetNumber;
    }
}
