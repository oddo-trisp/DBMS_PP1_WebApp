package gr.uoa.di.dbm.webapp.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {

    T findById(Long id);

    T findById(Object id);

    List findAll();

    void insert(T entity);

    T insertOrUpdate(T entity);

    void delete(T entity);

    void deleteById(Long entityId);
}
