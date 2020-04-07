package br.com.fiap.spring.entity;

import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Configurable
public class BaseAuditListener {

    @PrePersist
    public void touchForCreate(final BaseAudit entity) {
        final LocalDateTime now = LocalDateTime.now();
        entity.setCreationDate(now);
        entity.setUpdateDate(now);
    }

    @PreUpdate
    public void touchForUpdate(final BaseAudit entity) {
        entity.setUpdateDate( LocalDateTime.now() );
    }
}