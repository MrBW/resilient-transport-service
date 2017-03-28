package de.codecentric.resilient.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Benjamin Wilms
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnoteDTO extends FallbackAbstractDTO {

    @JsonProperty(required = true)
    private Long connote;

    public void setConnote(Long connote) {
        this.connote = connote;
    }

    public Long getConnote() {
        return connote;
    }

}
