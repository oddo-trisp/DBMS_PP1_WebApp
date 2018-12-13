package gr.uoa.di.dbm.webapp.dao;

import gr.uoa.di.dbm.webapp.entity.AppUser;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class AppUserDAO extends GenericDAO<AppUser> {

    public AppUser getUserProfile(String userName) {
        try {
            AppUser appUser = findByUsername(userName);
            if(appUser != null && CollectionUtils.isEmpty(appUser.getAppRoles()))
                Hibernate.initialize(appUser.getAppRoles());   //Init lazy collection
            return appUser;
        } catch (NoResultException e) {
            return null;
        }
    }

    public AppUser findByUsername(String userName) {
        try {
            return (AppUser) getEntityManager().createNamedQuery("AppUser.findByUsername")
                    .setParameter("userName", userName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
