package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "KNOWLEDGE_LEVEL")
public class KnowledgeLevel extends Entity<KnowledgeLevel> {

	private Teacher teacher;
	private KnowledgeGroup knowledgeGroup;
	private Integer level;

	@ManyToOne
	@JoinColumn(name = "TEACHER_ID")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@ManyToOne
    @JoinColumn(name = "KNOWLEDGE_ID")
	public KnowledgeGroup getKnowledgeGroup() {
		return knowledgeGroup;
	}

	public void setKnowledgeGroup(KnowledgeGroup knowledgeGroup) {
		this.knowledgeGroup = knowledgeGroup;
	}

	@Column(name = "LEVEL")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
