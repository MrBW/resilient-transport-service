package de.codecentric.resilient.booking.entity;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

/**
 * @author Benjamin Wilms
 */
@Embeddable
public class Address {

    @Basic
    private String country;

    @Basic
    private String city;

    @Basic
    private String postcode;

    @Basic
    private String street;

    @Basic
    private String streetNumber;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
