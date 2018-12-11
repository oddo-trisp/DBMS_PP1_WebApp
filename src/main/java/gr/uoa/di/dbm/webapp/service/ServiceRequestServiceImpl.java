package gr.uoa.di.dbm.webapp.service;

import gr.uoa.di.dbm.webapp.dao.GenericDAO;
import gr.uoa.di.dbm.webapp.dao.ServiceRequestDAO;
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
    private final ServiceRequestDAO serviceRequestDAO;

    @Autowired
    public ServiceRequestServiceImpl(@Qualifier("locationGenericDAO") GenericDAO<Location> locationDAO, ServiceRequestDAO serviceRequestDAO) {
        this.locationDAO = locationDAO;
        this.serviceRequestDAO = serviceRequestDAO;
    }

    public Location searchLocationById(Double lat, Double lon){
        LocationPK locationPK = new LocationPK(lat, lon);
        return locationDAO.findById(locationPK);
    }

    public Location insertOrUpdateLocation(Location location){
        return locationDAO.insertOrUpdate(location);
    }

    public void insertServiceRequest(ServiceRequest serviceRequest){
        serviceRequestDAO.insert(serviceRequest);
    }

    public List findServiceRequestTypes(){
        return serviceRequestDAO.findServiceRquestTypes();
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

    public List<ServiceRequest> findByZipCode(String zipCode){
        return serviceRequestDAO.findByZipCode(zipCode);
    }

    public List<ServiceRequest> findByAddress(String address){
        return serviceRequestDAO.findByAddress(address);
    }


}
