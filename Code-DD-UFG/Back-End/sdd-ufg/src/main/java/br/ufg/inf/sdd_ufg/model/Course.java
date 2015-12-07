package br.ufg.inf.sdd_ufg.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@javax.persistence.Entity
@Table(name = "COURSE")
public class Course extends Entity<Course> {

	private String name;
	private List<Grade> grades = new ArrayList<Grade>();

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonInclude(Include.NON_EMPTY)
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="course", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
}
