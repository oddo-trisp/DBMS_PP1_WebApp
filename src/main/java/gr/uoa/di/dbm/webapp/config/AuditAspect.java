package gr.uoa.di.dbm.webapp.config;

import gr.uoa.di.dbm.webapp.dao.AppUserDAO;
import gr.uoa.di.dbm.webapp.dao.AuditLogDAO;
import gr.uoa.di.dbm.webapp.entity.AppUser;
import gr.uoa.di.dbm.webapp.entity.AuditLog;
import gr.uoa.di.dbm.webapp.entity.AuditMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
            "&& !execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.insertServiceRequest(..))"
    )
    public void beforeDAOExec(JoinPoint joinPoint) {
        writeToLog(joinPoint, null);
    }

    @AfterReturning(pointcut = "execution(* gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl.insertServiceRequest(..))", returning = "result")
    public void afterInsert(JoinPoint joinPoint, Integer result){
        writeToLog(joinPoint, result);
    }

    private void writeToLog(JoinPoint joinPoint, Integer returnValue){
        System.err.println("before em find called : " + joinPoint);
        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String argument = "";
        for(Object arg : args)
            if(arg != null)
                argument = argument + arg.toString() + ", ";

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
            auditLog.setRequestId(returnValue);
            auditLogDAO.insert(auditLog);
        }
    }
}
