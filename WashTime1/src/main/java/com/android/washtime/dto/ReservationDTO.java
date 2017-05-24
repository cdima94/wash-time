package com.android.washtime.dto;

import java.sql.Time;
import java.util.Date;

import com.android.washtime.entity.DayOfWeek;
import com.android.washtime.entity.Student;

public class ReservationDTO {

	private Date mDate;
	private DayOfWeek mDayOfWeek;
	private Time mStartHour;
	private Time mEndHour;
	private String mRoomStudent;
	private Student mStudent;
	
	public Date getDate() {
		return mDate;
	}
	
	public void setDate(Date date) {
		mDate = date;
	}
	
	public DayOfWeek getDayOfWeek() {
		return mDayOfWeek;
	}
	
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		mDayOfWeek = dayOfWeek;
	}
	
	public Time getStartHour() {
		return mStartHour;
	}
	
	public void setStartHour(Time startHour) {
		mStartHour = startHour;
	}
	
	public Time getEndHour() {
		return mEndHour;
	}
	
	public void setEndHour(Time endHour) {
		mEndHour = endHour;
	}
	
	public String getRoomStudent() {
		return mRoomStudent;
	}
	
	public void setRoomStudent(String roomStudent) {
		mRoomStudent = roomStudent;
	}

	public Student getStudent() {
		return mStudent;
	}

	public void setStudent(Student student) {
		mStudent = student;
	}
}
