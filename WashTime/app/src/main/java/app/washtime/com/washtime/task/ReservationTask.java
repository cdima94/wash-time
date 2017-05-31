package app.washtime.com.washtime.task;

import java.util.List;

import app.washtime.com.washtime.entity.Reservation;

public interface ReservationTask {

    List<Reservation> getReservationList(Object... parameters);

    List<Reservation> getSpecificReservationList(Object... parameters);

    void reserve(Reservation reservation);

    String delete(Object... parameters);
}
