package br.ufg.inf.sdd_ufg.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ufg.inf.sdd_ufg.model.enums.StatusClazz;

@javax.persistence.Entity
@Table(name = "CLAZZ")
public class Clazz extends Entity<Clazz> {

	private DistributionProcess distributionProcess;
	private List<ClazzSchedule> clazzSchedules;
	private List<ClazzIntent> intents;
	private Teacher teacher;
	private Grade grade;
	private Integer workload;
	private String status;

	@ManyToOne
	@JoinColumn(name = "DIST_PROCESS_ID")
	public DistributionProcess getDistributionProcess() {
		return distributionProcess;
	}
	
	public void setDistributionProcess(DistributionProcess distributionProcess) {
		this.distributionProcess = distributionProcess;
	}
	
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="clazz", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<ClazzSchedule> getClazzSchedules() {
		return clazzSchedules;
	}

	public void setClazzSchedules(List<ClazzSchedule> clazzSchedules) {
		this.clazzSchedules = clazzSchedules;
	}

	@OneToMany( fetch = FetchType.EAGER,  mappedBy="clazz", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<ClazzIntent> getIntents() {
		return intents;
	}

	public void setIntents(List<ClazzIntent> intents) {
		this.intents = intents;
	}

	@ManyToOne
	@JoinColumn(name = "TEACHER_ID")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@ManyToOne
    @JoinColumn(name = "GRADE_ID")
    public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@Column(name = "WORKLOAD", length = 3)
	public Integer getWorkload() {
		return workload;
	}

	public void setWorkload(Integer workload) {
		this.workload = workload;
	}

	@Column(name = "STATUS", length = 12)
	public String getStatus() {
		if (status.equals(StatusClazz.CANCELED.toString())) {
			return status;
		} else if (teacher != null) {
			return StatusClazz.FINISHED.toString();
		} else if (intents.size() > 1) {
			return StatusClazz.CONFLICT.toString();
		}
		return StatusClazz.PENDING.toString();
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
