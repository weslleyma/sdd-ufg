package br.ufg.inf.sdd_ufg.jpa.exception;


public class FailedInstantiationDaoException extends RuntimeException {
	private static final long serialVersionUID = 225023159798717776L;

	public FailedInstantiationDaoException() {
		super("Objeto de entidade não pode ser criado!");
	}
}