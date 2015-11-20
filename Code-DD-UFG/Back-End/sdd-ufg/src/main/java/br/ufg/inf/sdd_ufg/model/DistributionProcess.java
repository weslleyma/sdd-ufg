package br.ufg.inf.sdd_ufg.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@javax.persistence.Entity
@Table(name = "DISTRIBUTION_PROCESS")
public class DistributionProcess extends Entity<DistributionProcess> {
    
	private String semester;
	private List<Clazz> clazzes;
	private Date clazzRegistryDate;
	private Date teacherIntentDate;
	private Date firstResolutionDate;
	private Date substituteDistribuitionDate;
	private Date finishDate;
	
	@Column(name = "SEMESTER")
	public String getSemester() {
		return semester;
	}
	
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="distributionProcess", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<Clazz> getClazzes() {
		return clazzes;
	}

	public void setClazzes(List<Clazz> clazzes) {
		this.clazzes = clazzes;
	}

	@Column(name="CLAZZ_REGISTRY_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getClazzRegistryDate() {
		return clazzRegistryDate;
	}
	
	public void setClazzRegistryDate(Date clazzRegistryDate) {
		this.clazzRegistryDate = clazzRegistryDate;
	}
	
	@Column(name="TEACHER_INTENT_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getTeacherIntentDate() {
		return teacherIntentDate;
	}
	
	public void setTeacherIntentDate(Date teacherIntentDate) {
		this.teacherIntentDate = teacherIntentDate;
	}
	
	@Column(name="FIRST_RESOLUTION_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getFirstResolutionDate() {
		return firstResolutionDate;
	}
	
	public void setFirstResolutionDate(Date firstResolutionDate) {
		this.firstResolutionDate = firstResolutionDate;
	}
	
	@Column(name="SUBSTITUTE_DIST_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getSubstituteDistribuitionDate() {
		return substituteDistribuitionDate;
	}
	
	public void setSubstituteDistribuitionDate(Date substituteDistribuitionDate) {
		this.substituteDistribuitionDate = substituteDistribuitionDate;
	}
	
	@Column(name="FINISH_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getFinishDate() {
		return finishDate;
	}
	
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	
   
}
