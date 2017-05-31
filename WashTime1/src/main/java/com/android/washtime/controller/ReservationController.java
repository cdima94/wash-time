package com.android.washtime.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.android.washtime.entity.DayOfWeek;
import com.android.washtime.entity.Reservation;
import com.android.washtime.entity.Student;
import com.android.washtime.repository.ReservationRepository;
import com.android.washtime.repository.StudentRepository;
import com.android.washtime.utils.DateParsing;

@Controller
public class ReservationController {

	@Autowired
	ReservationRepository reservationRepo;
	
	@Autowired
	StudentRepository studentRepo;
	
	@ResponseBody
	@RequestMapping(value = "/saveReservation", method = RequestMethod.POST)
	public String saveStuident(@RequestBody Reservation reservation, HttpServletResponse response) {
		Student student = studentRepo.findByUserName(reservation.getStudent().getUserName()).get();
		reservation.setStudent(student);
		reservationRepo.save(reservation);
		String.valueOf(response.getStatus());
		return "true";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getReservationList/{day}/{date}/{locationName}/{name}/{room}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Reservation> getReservationList(@PathVariable String day, @PathVariable String date, @PathVariable String locationName, @PathVariable String name, @PathVariable String room) {
		List<Reservation> retList = new ArrayList<>();
		DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
		int floor = Integer.valueOf(room)/100;
		List<Reservation> tempList = reservationRepo.findReservationByDayAndDateAndStudentStudentHomeLocationNameAndStudentStudentHomeName(dayOfWeek, date, locationName, name);
		for (Reservation res: tempList) {
			if (res.getStudent().getRoom() /100 == floor) {
				retList.add(res);
			}
		}
		return retList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete/{date}/{locationName}/{name}/{room}/{startHour}/{endHour}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String getSpecificReservation(@PathVariable String date,  @PathVariable String locationName, @PathVariable String name, @PathVariable String room, @PathVariable String startHour, @PathVariable String endHour, HttpServletResponse response) {
		reservationRepo.delete(reservationRepo.findReservationByDateAndAndStudentStudentHomeLocationNameAndStudentStudentHomeNameAndStudentRoomAndStartHourAndEndHour(date, locationName, name, Integer.valueOf(room), startHour, endHour).get());
		reservationRepo.flush();
		return String.valueOf(response.getStatus());
	}
	
	@ResponseBody
	@RequestMapping(value = "/getSpecificReservation/{startDate}/{endDate}/{locationName}/{name}/{room}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Reservation> getSpecificReservation(@PathVariable String startDate, @PathVariable String endDate, @PathVariable String locationName, @PathVariable String name, @PathVariable String room) {
		ArrayList<Reservation> retList =  new ArrayList<>();
		String[] splitStartDate = startDate.split("-");
		String[] splitEndDate = endDate.split("-");
		LocalDate startLocalDate = LocalDate.of(Integer.valueOf(splitStartDate[0]), Integer.valueOf(splitStartDate[1]), Integer.valueOf(splitStartDate[2]));
		LocalDate endLocalDate = LocalDate.of(Integer.valueOf(splitEndDate[0]), Integer.valueOf(splitEndDate[1]), Integer.valueOf(splitEndDate[2]));
		while (startLocalDate.isBefore(endLocalDate)) {
			retList.addAll(reservationRepo.findReservationByDateAndStudentStudentHomeLocationNameAndStudentStudentHomeNameAndStudentRoom(DateParsing.formatDate(startLocalDate.toString()), locationName, name, Integer.valueOf(room)));
			startLocalDate = startLocalDate.plusDays(1);
		}
		return retList;
	}
}
