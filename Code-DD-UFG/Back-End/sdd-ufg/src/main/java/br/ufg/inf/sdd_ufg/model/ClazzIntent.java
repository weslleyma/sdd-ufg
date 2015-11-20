package br.ufg.inf.sdd_ufg.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "CLAZZ_INTENT")
public class ClazzIntent extends Entity<ClazzIntent> {

	private Teacher teacher;
	private Clazz clazz;

	@ManyToOne
	@JoinColumn(name = "TEACHER_ID")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@ManyToOne
	@JoinColumn(name = "CLAZZ_ID")
	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

}
