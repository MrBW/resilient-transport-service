package de.codecentric.resilient.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Benjamin Wilms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponseStatus {

    private String serviceName;

    private String status;

    private String error;

    public ServiceResponseStatus(String serviceName, String status, String error) {
        this.serviceName = serviceName;
        this.status = status;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
