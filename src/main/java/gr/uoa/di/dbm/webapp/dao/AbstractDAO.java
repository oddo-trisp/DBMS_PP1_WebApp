package gr.uoa.di.dbm.webapp.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public abstract class AbstractDAO<T extends Serializable> {

    private Class<T> entityClass;

    @PersistenceContext
    EntityManager entityManager;

    public final void setEntityClass( Class< T > entityClassToSet ){
        this.entityClass = entityClassToSet;
    }

    public T findById(Long id){
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

