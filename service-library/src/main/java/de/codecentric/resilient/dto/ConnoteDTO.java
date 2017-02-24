package de.codecentric.resilient.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Benjamin Wilms
 */
public class ConnoteDTO extends FallbackAbstractDTO {

    @JsonProperty(required = true)
    private Long connote;

    @JsonProperty(required = true)
    private Date created;

    public void setConnote(Long connote) {
        this.connote = connote;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getConnote() {
        return connote;
    }

    public Date getCreated() {
        return created;
    }
}
