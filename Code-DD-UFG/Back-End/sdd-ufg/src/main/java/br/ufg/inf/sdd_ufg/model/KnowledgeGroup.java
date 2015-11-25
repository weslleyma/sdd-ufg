package br.ufg.inf.sdd_ufg.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.persistence.Entity
@Table(name = "KNOWLEDGE_GROUP")
public class KnowledgeGroup extends Entity<KnowledgeGroup> {
	
	private List<Grade> grades;
	private List<KnowledgeLevel> knowledgeLevels;
	private String name;

	@JsonInclude(Include.NON_EMPTY)
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="knowledgeGroup", cascade=CascadeType.ALL, orphanRemoval=true )
    public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
	
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("knowledge_levels")
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="knowledgeGroup", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<KnowledgeLevel> getKnowledgeLevels() {
		return knowledgeLevels;
	}

	public void setKnowledgeLevels(List<KnowledgeLevel> knowledgeLevels) {
		this.knowledgeLevels = knowledgeLevels;
	}

	@Column(name = "NAME", length=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
