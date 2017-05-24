package com.android.washtime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.android.washtime.entity.DayOfWeek;
import com.android.washtime.entity.Reservation;

@RepositoryRestResource
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	List<Reservation> findReservationByDay(DayOfWeek day);
	List<Reservation> findReservationByDayAndDateAndStudentStudentHomeLocationNameAndStudentStudentHomeName(DayOfWeek day, String date, String locationName, String name);
	List<Reservation> findReservationByDateAndStudentStudentHomeLocationNameAndStudentStudentHomeNameAndStudentRoom(String date, String locationName, String name, int room);
}
