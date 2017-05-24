package app.washtime.com.washtime.entity;

import java.io.Serializable;

public class Rule implements Serializable {

    private int id;
    private int noReservations;
    private String duration;
    private String startHour;
    private String endHour;
    private int floor;
    private StudentHome studentHome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return studentHome;
    }

    public void setRuleHome(StudentHome studentHome) {
        this.studentHome = studentHome;
    }
}
