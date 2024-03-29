package gr.uoa.di.dbm.webapp.dao;

import gr.uoa.di.dbm.webapp.entity.ServiceRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
@Transactional
public class ServiceRequestDAO extends GenericDAO<ServiceRequest>{

    public List callProcedure1(String sdate, String edate){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedStartDate = dateFormat.parse(sdate + " 00:00:00");
            Date parsedEndDate = dateFormat.parse(edate + " 00:00:00");
            Timestamp timestampStart = new Timestamp(parsedStartDate.getTime());
            Timestamp timestampEnd = new Timestamp(parsedEndDate.getTime());
            return entityManager.createNamedStoredProcedureQuery("ServiceRequest.procedure1")
                    .setParameter("startDate", timestampStart)
                    .setParameter("endDate", timestampEnd)
                    .getResultList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List callProcedure2(String sdate, String edate, String reqtype){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedStartDate = dateFormat.parse(sdate + " 00:00:00");
            Date parsedEndDate = dateFormat.parse(edate + " 00:00:00");
            Timestamp timestampStart = new Timestamp(parsedStartDate.getTime());
            Timestamp timestampEnd = new Timestamp(parsedEndDate.getTime());
            return entityManager.createNamedStoredProcedureQuery("ServiceRequest.procedure2")
                    .setParameter("startDate", timestampStart)
                    .setParameter("endDate", timestampEnd)
                    .setParameter("reqType", reqtype)
                    .getResultList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List callProcedure3(String sdate){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(sdate + " 00:00:00");
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            return entityManager.createNamedStoredProcedureQuery("ServiceRequest.procedure3")
                    .setParameter("startDate", timestamp)
                    .getResultList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List zipSearch(String zipcode){
        return entityManager.createNamedStoredProcedureQuery("ServiceRequest.zip_search")
                .setParameter("zipCode", zipcode)
                .getResultList();
    }

    public List addressSearch(String address){
        return entityManager.createNamedStoredProcedureQuery("ServiceRequest.address_search")
                .setParameter("address", address)
                .getResultList();
    }

    public List search(String zip, String address, String type){
        return entityManager.createNamedStoredProcedureQuery("ServiceRequest.search")
                .setParameter("zip", zip)
                .setParameter("address", address)
                .setParameter("type", type)
                .getResultList();
    }

    public List findServiceRquestStatus(){
        return entityManager
                .createNamedQuery("ServiceRequest.findRequestStatus",String.class)
                .getResultList();
    }

    public List findServiceRequestCurrentActivity(){
        return entityManager
                .createNamedQuery("ServiceRequest.findRequestCurrentActivity",String.class)
                .getResultList();
    }


    public String findLastReqNo(){
        return entityManager.createNamedQuery("ServiceRequest.findLatestReqNo", String.class)
                .setMaxResults(1)
                .getSingleResult();
    }
}
