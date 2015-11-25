package br.ufg.inf.sdd_ufg.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ufg.inf.sdd_ufg.dao.EntityDao;
import br.ufg.inf.sdd_ufg.jpa.exception.FailedInstantiationDaoException;
import br.ufg.inf.sdd_ufg.model.Entity;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class EntityDaoJpa<E extends Entity<E>> implements EntityDao<E> {

	protected Class<E> entityClass;

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	
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
		return entityManagerProvider.get();
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
    
    @Transactional
    public E save(E entity) {
    	return entity.getPersistent() ? update(entity) : insert(entity);
    };

    @Transactional
    public E insert(E entity) {
    	entity.setId(null);
    	getEntityManager().persist(preInsert(consist(entity)));
    	return entity;
    };

    
    @Transactional
    public void delete(E entity) {
    	getEntityManager().remove(preDelete(entity));
    }
    
    @Transactional
	public void delete(Long id) {
    	E entity = findById(id, 0);
    	if (entity == null) {
    		throw new IllegalArgumentException();
    	}
    	getEntityManager().remove(preDelete(entity));
    }
    

	@SuppressWarnings("unchecked")
	public E findById(Long id, Integer depth) {
		Object entity = getEntityManager().find(entityClass, id);
		if (entity != null) {
			entity = treatEntityDepth(entity, 0, depth);
		}
		return (E) entity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<E> findAll(Integer depth) {
		Query query = getEntityManager().createQuery("from " + entityClass.getSimpleName() + " " +  entityClass.getSimpleName().substring(0,1));
		List entities = query.getResultList();
		for (Object entity : entities) {
			entity = treatEntityDepth(entity, 0, depth);
		}
		return (List<E>) entities;
	}
	
	@SuppressWarnings("unchecked")
	private Object treatEntityDepth(Object entity, Integer depth, Integer maxDepth) {
		Field[] fields = entity.getClass().getDeclaredFields();  
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			try {
				if (Entity.class.isAssignableFrom(fields[i].getType()) && maxDepth > depth) {
					if (fields[i].get(entity) != null) {
						fields[i].set(entity, treatEntityDepth(fields[i].get(entity), depth + 1, maxDepth));
					}
				} else if (List.class.isAssignableFrom(fields[i].getType()) && maxDepth > depth) { 
					if (fields[i].get(entity) != null) {
						List<Object> list = (List<Object>) fields[i].get(entity);
						for (Object object : list) {
							if (object instanceof Entity<?>) {
								object = treatEntityDepth(object, depth + 1, maxDepth);
							}
						}
					}
				} else if (Entity.class.isAssignableFrom(fields[i].getType())) {
					fields[i].set(entity, null);
				} else if (List.class.isAssignableFrom(fields[i].getType())) {
					fields[i].set(entity, new ArrayList<E>());
				}
			} catch (IllegalAccessException iae) {}
			fields[i].setAccessible(false);
		} 
		return entity;
	}

	@Transactional
	public E refresh(E entity) {
		if (getEntityManager().contains(entity)) {
    		getEntityManager().refresh(entity);
    	} else {
    		entity = getEntityManager().find(entityClass, entity.getId());
    	}
    	return entity;
	}

	@Transactional
	public E update(E entity) {
		return getEntityManager().merge(preUpdate(consist(entity)));
	};
	
	public E preInsert(E entity) {
		return entity;
	}
	
	public E preUpdate(E entity) {
		return entity;
	}
	
	public E preDelete(E entity) {
		return entity;
	}
}
