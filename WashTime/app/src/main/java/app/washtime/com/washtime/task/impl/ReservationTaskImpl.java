package app.washtime.com.washtime.task.impl;


import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import app.washtime.com.washtime.constants.ConfigConstants;
import app.washtime.com.washtime.entity.DayOfWeek;
import app.washtime.com.washtime.entity.Reservation;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.task.ReservationTask;

public class ReservationTaskImpl implements ReservationTask {

    public List<Reservation> getReservationList(Object... parameters) {
        try {
            return new GetReservationList().execute(parameters).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> getSpecificReservationList(Object... parameters) {
        try {
            return new GetSpecificReservationList().execute(parameters).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void reserve(Reservation reservation) {
        new CreateNewReservation().execute(reservation);
    }

    @Override
    public String delete(Object... parameters) {
        try {
            return new Delete().execute(parameters).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> getHistoryList(Object... parameters) {
        try {
            return new GetHistoryList().execute(parameters).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetReservationList extends AsyncTask<Object, Integer, List<Reservation>> {

        protected List<Reservation> doInBackground(Object... parameters) {
            List<Reservation> reservations = new ArrayList<>();
            try {
                final String url = "http://"+ ConfigConstants.IP +"/getReservationList/"
                        + ((DayOfWeek)parameters[0]).getName() + "/"
                        + (String)parameters[1] + "/"
                        + (String)parameters[2] + "/"
                        + (String)parameters[3] + "/"
                        + String.valueOf(parameters[4]);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<LinkedHashMap> linkedMap = restTemplate.getForObject(url, List.class);
                for (LinkedHashMap linkedHashMap: linkedMap) {
                    Reservation reservation = new Reservation();
                    reservation.setDay(DayOfWeek.valueOf((String)linkedHashMap.get("day")));
                    reservation.setDate((String)linkedHashMap.get("date"));
                    reservation.setStartHour((String)linkedHashMap.get("startHour"));
                    reservation.setEndHour((String)linkedHashMap.get("endHour"));
                    LinkedHashMap studentMap = (LinkedHashMap) linkedHashMap.get("student");
                    Student studentItem = new Student();
                    studentItem.setLastName((String)studentMap.get("lastName"));
                    studentItem.setFirstName((String)studentMap.get("firstName"));
                    studentItem.setUserName((String)studentMap.get("userName"));
                    studentItem.setRoom((Integer)studentMap.get("room"));
                    reservation.setStudent(studentItem);
                    reservations.add(reservation);
                }
                return reservations;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
    }

    private class CreateNewReservation extends AsyncTask<Reservation, Void, String> {

        @Override
        protected String doInBackground(Reservation... params) {
            try {
                final String url = "http://" + ConfigConstants.IP + "/saveReservation";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<String> postResponse = restTemplate.postForEntity(url, params[0], String.class);
                return postResponse.getStatusCode().toString();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
    }

    private class GetSpecificReservationList extends AsyncTask<Object, Integer, List<Reservation>> {

        protected List<Reservation> doInBackground(Object... parameters) {
            List<Reservation> reservations = new ArrayList<>();
            try {
                final String url = "http://"+ ConfigConstants.IP +"/getSpecificReservation/"
                        + (String)parameters[0] + "/"
                        + (String)parameters[1] + "/"
                        + (String)parameters[2] + "/"
                        + (String)parameters[3] + "/"
                        + String.valueOf(parameters[4]);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<LinkedHashMap> linkedMap = restTemplate.getForObject(url, List.class);
                for (LinkedHashMap linkedHashMap: linkedMap) {
                    Reservation reservation = new Reservation();
                    reservation.setDay(DayOfWeek.valueOf((String)linkedHashMap.get("day")));
                    reservation.setDate((String)linkedHashMap.get("date"));
                    reservation.setStartHour((String)linkedHashMap.get("startHour"));
                    reservation.setEndHour((String)linkedHashMap.get("endHour"));
                    LinkedHashMap studentMap = (LinkedHashMap) linkedHashMap.get("student");
                    Student studentItem = new Student();
                    studentItem.setLastName((String)studentMap.get("lastName"));
                    studentItem.setFirstName((String)studentMap.get("firstName"));
                    studentItem.setUserName((String)studentMap.get("userName"));
                    studentItem.setRoom((Integer)studentMap.get("room"));
                    reservation.setStudent(studentItem);
                    reservations.add(reservation);
                }
                return reservations;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
    }

    private class Delete extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            final String url = "http://"+ ConfigConstants.IP +"/delete/"
                    + (String)params[0] + "/"
                    + (String)params[1] + "/"
                    + (String)params[2] + "/"
                    + String.valueOf(params[3]) + "/"
                    + (String)params[4] + "/"
                    + (String)params[5];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            String response = restTemplate.getForObject(url, String.class);
            return response;
        }
    }

    private class GetHistoryList extends AsyncTask<Object, Integer, List<Reservation>> {

        @Override
        protected List<Reservation> doInBackground(Object... params) {
            List<Reservation> reservations = new ArrayList<>();
            try {
                final String url = "http://"+ ConfigConstants.IP +"/getHistoryReservation/"
                        + (String) params[0] + "/"
                        + (String) params[1] + "/"
                        + (String) params[2] + "/"
                        + (String) params[3] + "/"
                        + String.valueOf(params[4]);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<LinkedHashMap> linkedMap = restTemplate.getForObject(url, List.class);
                for (LinkedHashMap linkedHashMap: linkedMap) {
                    Reservation reservation = new Reservation();
                    reservation.setDay(DayOfWeek.valueOf((String)linkedHashMap.get("day")));
                    reservation.setDate((String)linkedHashMap.get("date"));
                    reservation.setStartHour((String)linkedHashMap.get("startHour"));
                    reservation.setEndHour((String)linkedHashMap.get("endHour"));
                    LinkedHashMap studentMap = (LinkedHashMap) linkedHashMap.get("student");
                    Student studentItem = new Student();
                    studentItem.setLastName((String)studentMap.get("lastName"));
                    studentItem.setFirstName((String)studentMap.get("firstName"));
                    studentItem.setUserName((String)studentMap.get("userName"));
                    studentItem.setRoom((Integer)studentMap.get("room"));
                    reservation.setStudent(studentItem);
                    reservations.add(reservation);
                }
                return reservations;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
    }
}
