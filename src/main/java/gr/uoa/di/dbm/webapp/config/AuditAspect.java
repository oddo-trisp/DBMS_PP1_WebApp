package gr.uoa.di.dbm.webapp.config;

import gr.uoa.di.dbm.webapp.dao.AppUserDAO;
import gr.uoa.di.dbm.webapp.dao.AuditLogDAO;
import gr.uoa.di.dbm.webapp.entity.AppUser;
import gr.uoa.di.dbm.webapp.entity.AuditLog;
import gr.uoa.di.dbm.webapp.entity.AuditMessage;
import gr.uoa.di.dbm.webapp.entity.ServiceRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;

@Aspect
@Configuration
public class AuditAspect {

    final AppUserDAO appUserDAO;

    final AuditLogDAO auditLogDAO;

    @Autowired
    public AuditAspect(AppUserDAO appUserDAO, AuditLogDAO auditLogDAO) {
        this.appUserDAO = appUserDAO;
        this.auditLogDAO = auditLogDAO;
    }

    @Before("execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.*(..)) " +
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.insertOrUpdateLocation(..))" +
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.findServiceRequestStatus(..))" +
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.findServiceRequestCurrentActivity(..))" +
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.findLastReqNo(..))"+
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.findLogAll(..))"+
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.findLogByUsername(..))"+
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.insertServiceRequest(..))"+
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.findServiceRequestById(..))"+
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.updateServiceRequest(..))"
    )
    public void beforeDAOExec(JoinPoint joinPoint) {
        writeToLog(joinPoint, null, null);
    }

    @AfterReturning(pointcut = "execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.insertServiceRequest(..))" +
            "|| execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.updateServiceRequest(..))", returning = "result"
    )
    public void afterInsert(JoinPoint joinPoint, ServiceRequest result){
        writeToLog(joinPoint, result.getServiceRequestId(), result.getCreateDate());
    }

    private void writeToLog(JoinPoint joinPoint, Long requestId, Timestamp creationDate){
        System.err.println("before em find called : " + joinPoint);
        String name = joinPoint.getSignature().getName();
        String argument = "";
        if(requestId == null) {      //Search query
            Object[] args = joinPoint.getArgs();
            for (Object arg : args)
                if (arg != null)
                    argument = argument + arg.toString() + ", ";
        }

        String message = AuditMessage.getValueByKey(name);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        System.out.println("User: "+userName+" "+message+argument);
        String auditMessage = "User: "+userName+" "+ message + argument;

        AppUser appUser = appUserDAO.findByUsername(userName);
        if(appUser != null) {
            AuditLog auditLog = new AuditLog();
            auditLog.setUserId(appUser);
            auditLog.setActionMessage(auditMessage);
            auditLog.setRequestId(requestId);
            auditLog.setCreateDate(creationDate != null
                    ? creationDate
                    : new Timestamp(System.currentTimeMillis()));
            auditLogDAO.insert(auditLog);
        }
    }
}
