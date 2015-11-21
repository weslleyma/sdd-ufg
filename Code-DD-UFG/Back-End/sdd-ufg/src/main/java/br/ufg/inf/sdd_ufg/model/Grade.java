package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "GRADE")
public class Grade extends Entity<Grade> {

	private Course course;
	private KnowledgeGroup knowledgeGroup;
	private String name;

	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@ManyToOne
	@JoinColumn(name = "KNOWLEDGE_ID")
	public KnowledgeGroup getKnowledgeGroup() {
		return knowledgeGroup;
	}

	public void setKnowledgeGroup(KnowledgeGroup knowledgeGroup) {
		this.knowledgeGroup = knowledgeGroup;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
