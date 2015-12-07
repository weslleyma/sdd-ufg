package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.persistence.Entity
@Table(name = "KNOWLEDGE_LEVEL")
public class KnowledgeLevel extends Entity<KnowledgeLevel> {

	private Long teacherId;
	private Teacher teacher;
	private Long knowledgeGroupId;
	private KnowledgeGroup knowledgeGroup;
	private Integer level;

	@Transient
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("knowledge_id")
	public Long getKnowledgeGroupId() {
		return knowledgeGroupId;
	}

	public void setKnowledgeGroupId(Long knowledgeGroupId) {
		this.knowledgeGroupId = knowledgeGroupId;
	}

	@ManyToOne
	@JoinColumn(name = "TEACHER_ID")
	@JsonInclude(Include.NON_NULL)
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("teacher_id")
	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	@ManyToOne
    @JoinColumn(name = "KNOWLEDGE_ID")
	@JsonInclude(Include.NON_NULL)
	public KnowledgeGroup getKnowledgeGroup() {
		return knowledgeGroup;
	}

	public void setKnowledgeGroup(KnowledgeGroup knowledgeGroup) {
		this.knowledgeGroup = knowledgeGroup;
	}

	@Column(name = "LEVEL", length = 1)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
