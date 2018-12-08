package gr.uoa.di.dbm.webapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class GenericDAO<T extends Serializable> implements IGenericDAO<T> {

    private Class<T> entityClass;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public GenericDAO() {
        this.entityClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), GenericDAO.class);
    }

    public void setEntityClass(Class<T> classToSet){
        this.entityClass = classToSet;
    }

    public T findById(Long id){
        return entityManager.find(entityClass, id);
    }

    public T findById(Object id){
        return entityManager.find(entityClass, id);
    }

    public List findAll(){

        return entityManager.createQuery("from " + entityClass.getName())
                .getResultList();
    }

    public void insert(T entity){
        entityManager.persist(entity);
    }

    public T insertOrUpdate(T entity){
        return entityManager.merge(entity);
    }

    public void delete(T entity){
        entityManager.remove( entity );
    }

    public void deleteById(Long entityId){
        T entity = findById(entityId);
        delete(entity);
    }
}

