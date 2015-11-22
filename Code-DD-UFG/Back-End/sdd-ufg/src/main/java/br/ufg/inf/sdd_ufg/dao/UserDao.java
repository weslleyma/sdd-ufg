package br.ufg.inf.sdd_ufg.dao;

import br.ufg.inf.sdd_ufg.model.User;

public interface UserDao extends EntityDao<User> {
	
	User findUserByUsername(String userName);
	User findUserByToken(String token);
}
