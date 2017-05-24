package com.android.washtime.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Rule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "no_reservation", nullable = false)
	private int noReservations;
	
	@Column(name = "duration", nullable = false, length = 45)
	private String duration;
	
	@Column(name = "start_hour", nullable = false, length = 45)
	private String startHour;
	
	@Column(name = "end_hour", nullable = false, length = 45)
	private String endHour;
	
	@Column(name = "floor", nullable = false)
	private int floor;
	
	@JoinColumn(name = "ah_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private StudentHome ruleHome;

	public int getNoReservations() {
		return noReservations;
	}

	public void setNoReservations(int noReservations) {
		this.noReservations = noReservations;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public StudentHome getStudentHome() {
		return ruleHome;
	}

	public void setStudentHome(StudentHome studentHome) {
		this.ruleHome = studentHome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + floor;
		result = prime * result + id;
		result = prime * result + ((ruleHome == null) ? 0 : ruleHome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (floor != other.floor)
			return false;
		if (id != other.id)
			return false;
		if (ruleHome == null) {
			if (other.ruleHome != null)
				return false;
		} else if (!ruleHome.equals(other.ruleHome))
			return false;
		return true;
	}
}
