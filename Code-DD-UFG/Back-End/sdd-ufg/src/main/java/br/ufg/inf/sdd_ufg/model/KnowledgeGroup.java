package br.ufg.inf.sdd_ufg.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "KNOWLEDGE_GROUP")
public class KnowledgeGroup extends Entity<KnowledgeGroup> {
	
	private List<Grade> grades;
	private List<KnowledgeLevel> knowledgeLevels;
	private String name;

	@OneToMany( fetch = FetchType.EAGER,  mappedBy="knowledgeGroup", cascade=CascadeType.ALL, orphanRemoval=true )
    public List<Grade> getGrades() {
		return grades;
	}

	public void setGrade(List<Grade> grades) {
		this.grades = grades;
	}
	
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="knowledgeGroup", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<KnowledgeLevel> getKnowledgeLevels() {
		return knowledgeLevels;
	}

	public void setKnowledgeLevels(List<KnowledgeLevel> knowledgeLevels) {
		this.knowledgeLevels = knowledgeLevels;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
