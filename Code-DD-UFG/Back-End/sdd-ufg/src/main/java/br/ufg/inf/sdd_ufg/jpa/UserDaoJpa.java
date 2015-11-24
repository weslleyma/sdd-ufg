package br.ufg.inf.sdd_ufg.jpa;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.Query;

import br.ufg.inf.sdd_ufg.dao.UserDao;
import br.ufg.inf.sdd_ufg.model.User;

public class UserDaoJpa extends EntityDaoJpa<User> implements UserDao {

	@SuppressWarnings("unchecked")
	public User findUserByUsername(String userName) {
		Query query = getEntityManager().createQuery("from " + entityClass.getSimpleName() + " " +  entityClass.getSimpleName().substring(0,1)
				+ " where " + entityClass.getSimpleName().substring(0,1) + ".username = ?");
		query.setParameter(1, userName);
		List<User> users = (List<User>) query.getResultList();
		if (users.size() == 1) {
			return users.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public User findUserByToken(String token) {
		Query query = getEntityManager().createQuery("from " + entityClass.getSimpleName() + " " +  entityClass.getSimpleName().substring(0,1)
				+ " where " + entityClass.getSimpleName().substring(0,1) + ".sessionToken = ?");
		query.setParameter(1, token);
		List<User> users = (List<User>) query.getResultList();
		if (users.size() == 1) {
			return users.get(0);
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public User preInsert(User user) {
		Query query = getEntityManager().createQuery("from " + entityClass.getSimpleName() + " " +  entityClass.getSimpleName().substring(0,1)
				+ " where " + entityClass.getSimpleName().substring(0,1) + ".username = ?"
				+ " or    " + entityClass.getSimpleName().substring(0,1) + ".teacher = ?");
		query.setParameter(1, user.getUserName());
		query.setParameter(2, user.getTeacher());
		List<User> users = (List<User>) query.getResultList();
		if (users.size() == 1) {
			throw new EntityExistsException();
		} 
		return super.preInsert(user);
	}
}
