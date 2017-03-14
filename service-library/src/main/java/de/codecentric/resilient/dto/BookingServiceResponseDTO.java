package de.codecentric.resilient.dto;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Benjamin Wilms
 */
public class BookingServiceResponseDTO extends FallbackAbstractDTO {

    @JsonProperty(required = true)
    private ConnoteDTO connoteDTO;

    @JsonProperty(required = true)
    private Date created;

    @JsonProperty(required = true)
    private String status;

    @JsonProperty(required = true)
    private CustomerDTO customerDTO;

    @JsonProperty(required = true, value = "service-response-status")
    private List<ServiceResponseStatus> serviceResponseStatusList;

    public List<ServiceResponseStatus> getServiceResponseStatusList() {
        return serviceResponseStatusList;
    }

    public void setServiceResponseStatusList(List<ServiceResponseStatus> serviceResponseStatusList) {
        this.serviceResponseStatusList = serviceResponseStatusList;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
