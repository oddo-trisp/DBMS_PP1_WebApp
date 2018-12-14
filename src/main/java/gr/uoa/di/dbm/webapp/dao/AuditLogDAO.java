package gr.uoa.di.dbm.webapp.dao;

import gr.uoa.di.dbm.webapp.entity.AuditLog;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AuditLogDAO extends GenericDAO<AuditLog> {

    public List<AuditLog> findByUsername(String username){
        return entityManager.createNamedQuery("AuditLog.findByUsername", AuditLog.class)
                .setParameter("userName", username)
                .getResultList();
    }
}
