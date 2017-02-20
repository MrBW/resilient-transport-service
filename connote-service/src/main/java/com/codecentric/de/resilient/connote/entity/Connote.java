package com.codecentric.de.resilient.connote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.joda.time.LocalDateTime;

/**
 * @author Benjamin Wilms
 */
@Entity
public class Connote {

    @Id
    private Long connote;

    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    public Connote(Long connote) {

        this.connote = connote;
    }

    public Connote() {
    }

    public Long getConnote() {
        return connote;
    }

    public void setConnote(Long connote) {
        this.connote = connote;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

}
