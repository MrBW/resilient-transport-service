package com.codecentric.de.resilient.dto;

import java.util.Date;

/**
 * @author Benjamin Wilms
 */

public class ConnoteDTO extends AbstractDTO {

    private Long connote;

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
