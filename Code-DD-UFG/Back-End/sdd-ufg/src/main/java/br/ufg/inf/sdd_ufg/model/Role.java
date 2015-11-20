package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "ROLE")
public class Role extends Entity<Role> {

	private String role;

	@Column(name = "ROLE")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
