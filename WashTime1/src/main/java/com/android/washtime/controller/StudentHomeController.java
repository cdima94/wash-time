package com.android.washtime.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.android.washtime.entity.StudentHome;
import com.android.washtime.repository.StudentHomeRepository;

@Controller
public class StudentHomeController {

	@Autowired
	StudentHomeRepository shRepo;
	
	@ResponseBody
	@RequestMapping(value = "/getAllLocations", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<StudentHome> getAllLocations() {
		List<StudentHome> studentHome = (List<StudentHome>) shRepo.findAll();
		return studentHome;
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveStudentHome", method = RequestMethod.POST)
	public String saveStudentHime(@RequestBody StudentHome studentHome, HttpServletResponse response) {
		shRepo.save(studentHome);
		return "true";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAllLocationsName", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<String> getAllLocationsName() {
		List<String> locationsName = new ArrayList<>();
		List<StudentHome> studentHome = (List<StudentHome>) shRepo.findAll();
		for (StudentHome sh: studentHome) {
			if (!locationsName.contains(sh.getLocationName())) {
				locationsName.add(sh.getLocationName());
			}
		}
		return locationsName;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAllNames/{locationName}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<String> getAllNames(@PathVariable String locationName) {
		List<String> locationsName = new ArrayList<>();
		List<StudentHome> studentHome = (List<StudentHome>) shRepo.findByLocationName(locationName);
		for (StudentHome sh: studentHome) {
			locationsName.add(sh.getName());
		}
		return locationsName;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getStudentHome/{locationName}/{name}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public StudentHome getStudentHome(@PathVariable String locationName, @PathVariable String name) {
		Optional<StudentHome> studentHome = shRepo.findByLocationNameAndName(locationName, name);
		if (studentHome.isPresent()) {
			return studentHome.get();
		}
		return null;
	}
}
