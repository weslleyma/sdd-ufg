package br.ufg.inf.sdd_ufg.dao;

import java.util.List;

import br.ufg.inf.sdd_ufg.model.Entity;

public interface EntityDao <E extends Entity<E>> {

	E create();
	
	E findById(Long id);

	List<E> findAll();

	E refresh(E entity);
	E include(E entity);
	E update(E entity);
	E exclude(E entity);
}
