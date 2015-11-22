package br.ufg.inf.sdd_ufg.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "CLAZZ")
public class Clazz extends Entity<Clazz> {

	private DistributionProcess distributionProcess;
	private List<ClazzSchedule> clazzSchedules;
	private Teacher teacher;
	private Grade grade;
	private Integer workload;
	private Integer code;

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
	

	@ManyToOne
    @JoinColumn(name = "GRADE_ID")
    public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@Column(name = "WORKLOAD")
	public Integer getWorkload() {
		return workload;
	}

	public void setWorkload(Integer workload) {
		this.workload = workload;
	}

	@Column(name = "CODE")
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
