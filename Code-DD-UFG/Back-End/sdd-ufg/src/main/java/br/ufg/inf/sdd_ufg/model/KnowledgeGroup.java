package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "KNOWLEDGE_GROUP")
public class KnowledgeGroup extends Entity<KnowledgeGroup> {
	
	private Grade grade;
	private Integer code;
	private String name;

	@ManyToOne
    @JoinColumn(name = "GRADE_ID")
    public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
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
