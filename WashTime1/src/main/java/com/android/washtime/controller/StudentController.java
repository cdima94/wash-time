package com.android.washtime.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.android.washtime.entity.Role;
import com.android.washtime.entity.Student;
import com.android.washtime.entity.StudentHome;
import com.android.washtime.repository.StudentHomeRepository;
import com.android.washtime.repository.StudentRepository;

@Controller
public class StudentController {

	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	StudentHomeRepository shRepo;
	
	@ResponseBody
	@RequestMapping(value = "/getStudent/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Student getUser(@PathVariable String firstName) {
		Student student = studentRepo.findByFirstName(firstName).get();
		System.out.println(student.getLastName());
		return student;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUser/{userName}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Student getUserByUserName(@PathVariable String userName) {
		Optional<Student> student = studentRepo.findByUserName(userName);
		if (!student.isPresent()) {
			return null;
		}
		return student.get();
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
	public String saveStudent(@RequestBody Student student, HttpServletResponse response) {
		student.setPasswordHash(new BCryptPasswordEncoder().encode(student.getPassword()));
		Optional<StudentHome> studentHome = shRepo.findByLocationNameAndName(student.getStudentHome().getLocationName(), student.getStudentHome().getName());
		if (studentHome.isPresent()) {
			student.setStudentHome(studentHome.get());
			studentRepo.save(student);
		}
		String.valueOf(response.getStatus());
		return "true";
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
	public String updateStudent(@RequestBody Student student, HttpServletResponse response) {
		Student tempStudent = studentRepo.findByUserName(student.getUserName()).get();
		tempStudent.setFirstName(student.getFirstName());
		tempStudent.setLastName(student.getLastName());
		tempStudent.setRoom(student.getRoom());
		tempStudent.setUserName(student.getUserName());
		studentRepo.save(tempStudent);
		return "true";
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePassword/{password}", method = RequestMethod.POST)
	public String updatePassword(@RequestBody Student student, HttpServletResponse response, @PathVariable String password) {
		Student tempStudent = studentRepo.findByUserName(student.getUserName()).get();
		tempStudent.setPassword(password);
		tempStudent.setPasswordHash(new BCryptPasswordEncoder().encode(password));
		studentRepo.save(tempStudent);
		return "true";
	}
	
	@ResponseBody
	@RequestMapping(value = "/login/{userName}/{password}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Student validateLogin(@PathVariable String userName, @PathVariable String password) {
		Student student = studentRepo.findByUserName(userName).get();
		if (student != null) {
			if (student.getRole().equals(Role.GENERAL_ADMIN)) {
				if (student.getPasswordHash().length() == 5) {
					return student;
				} else {
					if (new BCryptPasswordEncoder().matches(password, student.getPasswordHash())) {
						return student;
					}
				}
			}
			if (new BCryptPasswordEncoder().matches(password, student.getPasswordHash())) {
				return student;
			} else {
				return null;
			}
		}
		return null;
	}
}
