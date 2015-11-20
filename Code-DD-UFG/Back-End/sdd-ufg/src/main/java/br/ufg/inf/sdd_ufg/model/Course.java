package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "COURSE")
public class Course extends Entity<Course> {

	private Integer code;
	private String name;

	@Column(name = "CODE")
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
