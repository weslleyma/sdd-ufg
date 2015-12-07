package br.ufg.inf.sdd_ufg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.persistence.Entity
@Table(name = "DISTRIBUTION_PROCESS")
public class DistributionProcess extends Entity<DistributionProcess> {
    
	private String semester;
	private Date clazzRegistryDate;
	private Date teacherIntentDate;
	private Date firstResolutionDate;
	private Date substituteDistribuitionDate;
	private Date finishDate;
	private List<Clazz> clazzes = new ArrayList<Clazz>();
	
	@Column(name = "SEMESTER", length = 15)
	public String getSemester() {
		return semester;
	}
	
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	@JsonProperty("clazz_registry_date")
	@Column(name="CLAZZ_REGISTRY_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getClazzRegistryDate() {
		return clazzRegistryDate;
	}
	
	public void setClazzRegistryDate(Date clazzRegistryDate) {
		this.clazzRegistryDate = clazzRegistryDate;
	}
	
	@JsonProperty("teacher_intent_date")
	@Column(name="TEACHER_INTENT_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getTeacherIntentDate() {
		return teacherIntentDate;
	}
	
	public void setTeacherIntentDate(Date teacherIntentDate) {
		this.teacherIntentDate = teacherIntentDate;
	}
	
	@JsonProperty("first_resolution_date")
	@Column(name="FIRST_RESOLUTION_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getFirstResolutionDate() {
		return firstResolutionDate;
	}
	
	public void setFirstResolutionDate(Date firstResolutionDate) {
		this.firstResolutionDate = firstResolutionDate;
	}
	
	@JsonProperty("substitute_distribution_date")
	@Column(name="SUBSTITUTE_DIST_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getSubstituteDistribuitionDate() {
		return substituteDistribuitionDate;
	}
	
	public void setSubstituteDistribuitionDate(Date substituteDistribuitionDate) {
		this.substituteDistribuitionDate = substituteDistribuitionDate;
	}
	
	@JsonProperty("finish_date")
	@Column(name="FINISH_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getFinishDate() {
		return finishDate;
	}
	
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="process", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<Clazz> getClazzes() {
		return clazzes;
	}

	public void setClazzes(List<Clazz> clazzes) {
		this.clazzes = clazzes;
	}
   
}
