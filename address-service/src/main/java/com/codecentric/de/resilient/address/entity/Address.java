package com.codecentric.de.resilient.address.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Benjamin Wilms (xd98870)
 */

@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String country;

    private String city;

    private String postcode;

    private String street;

    private String streetNumber;

    public Address() {
    }

    /***
     *
     * @param country
     * @param city
     * @param postcode
     * @param street
     * @param streetNumber
     */
    public Address(String country, String city, String postcode, String street, String streetNumber) {
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
