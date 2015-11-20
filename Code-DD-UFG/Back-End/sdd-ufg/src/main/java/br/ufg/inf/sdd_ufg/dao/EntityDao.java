package br.ufg.inf.sdd_ufg.dao;

import java.util.List;

import br.ufg.inf.sdd_ufg.model.Entity;

public interface EntityDao <E extends Entity<E>> {

	E create();
	
	E findById(Long id, Integer depth);

	List<E> findAll(Integer depth);

	E refresh(E entity);
	
	E save(E entity);
	
	E insert(E entity);
	
	E update(E entity);
	
	void delete(E entity);
	void delete(Long id);
}
