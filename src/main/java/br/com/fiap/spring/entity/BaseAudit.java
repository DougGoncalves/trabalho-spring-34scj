package br.com.fiap.spring.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import java.io.Serializable;
import java.time.LocalDateTime;

@EntityListeners({BaseAuditListener.class})
public class BaseAudit implements Serializable {
    private static final long serialVersionUID = 9181767236051905605L;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
