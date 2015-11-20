package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@javax.persistence.Entity
@Table(name = "DISTRIBUTION_PROCESS")
public class DistributionProcess extends Entity<DistributionProcess> {
    
	private String semester;
	private LocalDate clazzRegistryDate;
	private LocalDate teacherIntentDate;
	private LocalDate firstResolutionDate;
	private LocalDate substituteDistribuitionDate;
	private LocalDate finishDate;
	
	@Column(name = "SEMESTER")
	public String getSemester() {
		return semester;
	}
	
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	@Column(name="CLAZZ_REGISTRY_DATE")
	@Type(type="br.ufg.inf.sdd_ufg.hibernate.type.LocalDateUserType")
	public LocalDate getClazzRegistryDate() {
		return clazzRegistryDate;
	}
	
	public void setClazzRegistryDate(LocalDate clazzRegistryDate) {
		this.clazzRegistryDate = clazzRegistryDate;
	}
	
	@Column(name="TEACHER_INTENT_DATE")
	@Type(type="br.ufg.inf.sdd_ufg.hibernate.type.LocalDateUserType")
	public LocalDate getTeacherIntentDate() {
		return teacherIntentDate;
	}
	
	public void setTeacherIntentDate(LocalDate teacherIntentDate) {
		this.teacherIntentDate = teacherIntentDate;
	}
	
	@Column(name="FIRST_RESOLUTION_DATE")
	@Type(type="br.ufg.inf.sdd_ufg.hibernate.type.LocalDateUserType")
	public LocalDate getFirstResolutionDate() {
		return firstResolutionDate;
	}
	
	public void setFirstResolutionDate(LocalDate firstResolutionDate) {
		this.firstResolutionDate = firstResolutionDate;
	}
	
	@Column(name="SUBSTITUTE_DIST_DATE")
	@Type(type="br.ufg.inf.sdd_ufg.hibernate.type.LocalDateUserType")
	public LocalDate getSubstituteDistribuitionDate() {
		return substituteDistribuitionDate;
	}
	
	public void setSubstituteDistribuitionDate(LocalDate substituteDistribuitionDate) {
		this.substituteDistribuitionDate = substituteDistribuitionDate;
	}
	
	@Column(name="FINISH_DATE")
	@Type(type="br.ufg.inf.sdd_ufg.hibernate.type.LocalDateUserType")
	public LocalDate getFinishDate() {
		return finishDate;
	}
	
	public void setFinishDate(LocalDate finishDate) {
		this.finishDate = finishDate;
	}
	
	
   
}
