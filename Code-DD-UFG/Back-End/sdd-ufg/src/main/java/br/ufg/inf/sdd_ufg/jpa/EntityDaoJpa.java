package br.ufg.inf.sdd_ufg.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufg.inf.sdd_ufg.dao.EntityDao;
import br.ufg.inf.sdd_ufg.jpa.exception.FailedInstantiationDaoException;
import br.ufg.inf.sdd_ufg.model.Entity;

public class EntityDaoJpa<E extends Entity<E>> implements EntityDao<E> {

	protected Class<E> entityClass;

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public EntityDaoJpa() {
		Class<?> clazz = getClass();
		while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
			clazz = clazz.getSuperclass();
		}
		try {
			ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
	        entityClass = (Class<E>) parameterizedType.getActualTypeArguments()[0];
		} catch (Exception e) {
			entityClass = null;
		}
	}
	
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	protected E consist(E entity) {
    	return entity.consist();
    }
	
	public E create() {
		try {
			return entityClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailedInstantiationDaoException();
		}
	}
    
    
    public E save(E entity) {
    	return entity.getPersistent() ? update(entity) : include(entity);
    };

    
    public E include(E entity) {
    	entity.setId(null);
    	getEntityManager().persist(consist(entity));
    	return entity;
    };
    
    
    public E remove(E entity) {
    	getEntityManager().remove(entity);
    	entity.setId(null);
    	return entity;
    };

    
	public E exclude(E entity) {
    	return getEntityManager().merge(entity);
    };
    

	public E findById(Long id) {
		return getEntityManager().find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		Query query = getEntityManager().createQuery("from "+entityClass.getSimpleName());
		return (List<E>) query.getResultList();
	}

	public E refresh(E entity) {
		if (getEntityManager().contains(entity)) {
    		getEntityManager().refresh(entity);
    	} else {
    		entity = getEntityManager().find(entityClass, entity.getId());
    	}
    	return entity;
	}

	public E update(E entity) {
		return getEntityManager().merge(consist(entity));
	};
}
