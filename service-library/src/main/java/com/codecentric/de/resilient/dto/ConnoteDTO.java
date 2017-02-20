package com.codecentric.de.resilient.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Benjamin Wilms
 */
public class ConnoteDTO extends AbstractDTO {

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
