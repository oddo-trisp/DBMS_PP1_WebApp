package gr.uoa.di.dbm.webapp.config;

import gr.uoa.di.dbm.webapp.dao.AppUserDAO;
import gr.uoa.di.dbm.webapp.dao.GenericDAO;
import gr.uoa.di.dbm.webapp.entity.AppUser;
import gr.uoa.di.dbm.webapp.entity.AuditLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Aspect
@Configuration
public class AuditAspect {

    //@PersistenceContext
    //private EntityManager entityManager;

    final AppUserDAO appUserDAO;

    final GenericDAO<AuditLog> auditLogDAO;

    @Autowired
    public AuditAspect(AppUserDAO appUserDAO, @Qualifier("auditLogGenericDAO") GenericDAO<AuditLog> auditLogDAO) {
        this.appUserDAO = appUserDAO;
        this.auditLogDAO = auditLogDAO;
    }


    //@Around("execution(* javax.persistence.EntityManager.createNamedStoredProcedureQuery(..))")
    //@Before("execution(* gr.uoa.di.dbm.webapp.service.*.*(..))")
    public Object aroundFind(JoinPoint joinPoint) {
        System.err.println("before em find called : " + joinPoint);
        Object o = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        AppUser appUser = appUserDAO.findByUsername("Odysseas");
        if(appUser != null) {
            AuditLog auditLog = new AuditLog();
            auditLog.setUserId(appUser);
            auditLog.setActionMessage("MESSaaaage!!!");
            auditLogDAO.insert(auditLog);
        }
        /*try {

            o = joinPoint.proceed();

            System.err.println("after em find advice called : " + joinPoint);

        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }*/
        return o;

    }

}
