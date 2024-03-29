package gr.uoa.di.dbm.webapp.service;

import gr.uoa.di.dbm.webapp.dao.AuditLogDAO;
import gr.uoa.di.dbm.webapp.dao.GenericDAO;
import gr.uoa.di.dbm.webapp.dao.ServiceRequestDAO;
import gr.uoa.di.dbm.webapp.entity.AuditLog;
import gr.uoa.di.dbm.webapp.entity.Location;
import gr.uoa.di.dbm.webapp.entity.LocationPK;
import gr.uoa.di.dbm.webapp.entity.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRequestServiceImpl {

    private final GenericDAO<Location> locationDAO;
    private final AuditLogDAO auditLogDAO;
    private final ServiceRequestDAO serviceRequestDAO;

    @Autowired
    public ServiceRequestServiceImpl(@Qualifier("locationGenericDAO") GenericDAO<Location> locationDAO, AuditLogDAO auditLogDAO, ServiceRequestDAO serviceRequestDAO) {
        this.locationDAO = locationDAO;
        this.auditLogDAO = auditLogDAO;
        this.serviceRequestDAO = serviceRequestDAO;
    }

    public Location insertOrUpdateLocation(Location location){
        return locationDAO.insertOrUpdate(location);
    }

    public ServiceRequest insertServiceRequest(ServiceRequest serviceRequest){
        serviceRequestDAO.insert(serviceRequest);
        return serviceRequest;
    }

    public ServiceRequest updateServiceRequest(ServiceRequest serviceRequest){
        return serviceRequestDAO.insertOrUpdate(serviceRequest);
    }

    public List findServiceRequestStatus(){
        return serviceRequestDAO.findServiceRquestStatus();
    }

    public List findServiceRequestCurrentActivity(){
        return serviceRequestDAO.findServiceRequestCurrentActivity();
    }

    public List storedProcedure1(String sdate, String edate){
        List result = serviceRequestDAO.callProcedure1(sdate,edate);
        return result;
    }

    public List storedProcedure2(String sdate, String edate, String reqtype){
        List result = serviceRequestDAO.callProcedure2(sdate,edate,reqtype);
        return result;
    }

    public List storedProcedure3(String sdate){
        List result = serviceRequestDAO.callProcedure3(sdate);
        return result;
    }

    public List searchByZip(String zipcode){
        List result = serviceRequestDAO.zipSearch(zipcode);
        return result;
    }

    public List searchByAddress(String address){
        List result = serviceRequestDAO.addressSearch(address);
        return result;
    }

    public List search(String zip, String address, String type){
        List result = serviceRequestDAO.search(zip,address,type);
        return result;
    }

    public String findLastReqNo(){
        return serviceRequestDAO.findLastReqNo();
    }


    public List<AuditLog> findLogByUsername(String username) {
        return auditLogDAO.findByUsername(username);
    }

    public List<AuditLog> findLogAll() {
        return auditLogDAO.findAll();
    }

    public ServiceRequest findServiceRequestById(Long id){
        return serviceRequestDAO.findById(id);
    }

}
