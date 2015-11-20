package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "CLAZZ_SCHEDULE")
public class ClazzSchedule extends Entity<ClazzSchedule> {

	private Clazz clazz;
	private Integer weekDay;
	private String startTime;
	private String endTime;
	
	@ManyToOne
	@JoinColumn(name = "CLAZZ_ID")
	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	@Column(name = "WEEK_DAY")
	public Integer getWeekDay() {
		return weekDay;
	}
	
	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}
	
	@Column(name = "START_TIME")
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Column(name = "END_TIME")
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
