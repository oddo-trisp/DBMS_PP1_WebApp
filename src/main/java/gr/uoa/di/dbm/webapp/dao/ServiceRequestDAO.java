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

    public List callProcedure1(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse("2014-01-02 00:00:00");
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            return entityManager.createNamedStoredProcedureQuery("ServiceRequest.procedure1")
                    .setParameter("startDate", timestamp)
                    .setParameter("endDate", timestamp)
                    .getResultList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
