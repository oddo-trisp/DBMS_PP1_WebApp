package gr.uoa.di.dbm.webapp.dao;

import gr.uoa.di.dbm.webapp.entity.AppRole;
import gr.uoa.di.dbm.webapp.entity.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class AppRoleDAO extends AbstractDAO<AppRole> {

    public List<String> getRoleNames(Long userId) {
        String sql = "Select ur.appRole.roleName from " + UserRole.class.getName() + " ur " //
                + " where ur.appUser.userId = :userId ";

        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public AppRole getRoleByName(String roleName) {
        String sql = "Select e from " + AppRole.class.getName() + " e " //
                + " Where e.roleName = :roleName ";

        Query query = this.entityManager.createQuery(sql, AppRole.class);
        query.setParameter("roleName", roleName);
        return (AppRole) query.getSingleResult();
    }

}
