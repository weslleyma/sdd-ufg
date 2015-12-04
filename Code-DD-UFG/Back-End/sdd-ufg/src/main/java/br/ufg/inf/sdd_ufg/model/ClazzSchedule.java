package br.ufg.inf.sdd_ufg.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.persistence.Entity
@Table(name = "CLAZZ_SCHEDULE")
public class ClazzSchedule extends Entity<ClazzSchedule> {

	private Integer weekDay;
	private String startTime;
	private String endTime;
	private List<Clazz> clazzes;

	@JsonProperty("week_day")
	@Column(name = "WEEK_DAY", length = 1)
	public Integer getWeekDay() {
		return weekDay;
	}
	
	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}
	
	@JsonProperty("start_time")
	@Column(name = "START_TIME", length = 5)
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@JsonProperty("end_time")
	@Column(name = "END_TIME", length = 5)
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@ManyToMany(mappedBy="schedules")
	public List<Clazz> getClazzes() {
		return clazzes;
	}

	public void setClazzes(List<Clazz> clazzes) {
		this.clazzes = clazzes;
	}

}
