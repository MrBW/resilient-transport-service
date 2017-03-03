package de.codecentric.resilient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Benjamin Wilms
 */
public class AddressResponseDTO extends FallbackAbstractDTO {

    @JsonProperty(required = true)
    private String country;

    @JsonProperty(required = true)
    private String city;

    @JsonProperty(required = true)
    private String postcode;

    @JsonProperty(required = true)
    private String street;

    @JsonProperty(required = true)
    private String streetNumber;

    public AddressResponseDTO() {
    }

    public AddressResponseDTO(String country, String city, String postcode, String street, String streetNumber) {
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.streetNumber = streetNumber;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddressDTO{");
        sb.append("country='").append(country).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", postcode='").append(postcode).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", streetNumber='").append(streetNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
