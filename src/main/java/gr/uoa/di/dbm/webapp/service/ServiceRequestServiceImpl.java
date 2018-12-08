package gr.uoa.di.dbm.webapp.service;

import gr.uoa.di.dbm.webapp.dao.GenericDAO;
import gr.uoa.di.dbm.webapp.dao.IGenericDAO;
import gr.uoa.di.dbm.webapp.entity.Location;
import gr.uoa.di.dbm.webapp.entity.LocationPK;
import gr.uoa.di.dbm.webapp.entity.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceRequestServiceImpl {

    private final IGenericDAO<Location> locationDAO;
    private final IGenericDAO<ServiceRequest> serviceRequestDAO;

    @Autowired
    public ServiceRequestServiceImpl(GenericDAO<Location> locationDAO, GenericDAO<ServiceRequest> serviceRequestDAO) {
        this.locationDAO = locationDAO;
        this.serviceRequestDAO = serviceRequestDAO;
        locationDAO.setEntityClass(Location.class);
        serviceRequestDAO.setEntityClass(ServiceRequest.class);
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


}
