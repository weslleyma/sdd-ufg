package br.ufg.inf.sdd_ufg.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.persistence.Entity
@Table(name = "GRADE")
public class Grade extends Entity<Grade> {

	private Long courseId;
	private Course course;
	private Long knowledgeGroupId;
	private KnowledgeGroup knowledgeGroup;
	private String name;
	private List<Clazz> clazzes = new ArrayList<Clazz>();

	@Transient
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("course_id")
	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	@JsonInclude(Include.NON_NULL)
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

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
	@JoinColumn(name = "KNOWLEDGE_ID")
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("knowledge")
	public KnowledgeGroup getKnowledgeGroup() {
		return knowledgeGroup;
	}

	public void setKnowledgeGroup(KnowledgeGroup knowledgeGroup) {
		this.knowledgeGroup = knowledgeGroup;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonInclude(Include.NON_EMPTY)
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="grade", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<Clazz> getClazzes() {
		return clazzes;
	}

	public void setClazzes(List<Clazz> clazzes) {
		this.clazzes = clazzes;
	}

	
}
