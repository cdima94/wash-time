package com.android.washtime.controller;

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

import com.android.washtime.entity.Rule;
import com.android.washtime.entity.StudentHome;
import com.android.washtime.repository.RuleRepository;
import com.android.washtime.repository.StudentHomeRepository;

@Controller
public class RuleController {

	@Autowired
	RuleRepository ruleRepo;
	
	@Autowired
	StudentHomeRepository shRepo;
	
	@ResponseBody
	@RequestMapping(value = "/getRule/{locationName}/{name}/{floor}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Rule getRule(@PathVariable String locationName, @PathVariable String name, @PathVariable int floor) {
		Optional<Rule> rule = ruleRepo.findRuleByRuleHomeLocationNameAndRuleHomeNameAndFloor(locationName, name, floor);
		if (rule.isPresent()) {
			return rule.get();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveRule", method = RequestMethod.POST)
	public String saveStudent(@RequestBody Rule rule, HttpServletResponse response) {
		Optional<Rule> tempRule = ruleRepo.findRuleByRuleHomeLocationNameAndRuleHomeNameAndFloor(rule.getStudentHome().getLocationName(), rule.getStudentHome().getName(), rule.getFloor());
		if (tempRule.isPresent()) {
			Rule rl = tempRule.get();
			rl.setDuration(rule.getDuration());
			rl.setEndHour(rule.getEndHour());
			rl.setStartHour(rule.getStartHour());
			rl.setNoReservations(rule.getNoReservations());
			ruleRepo.saveAndFlush(rl);
		} else {
			StudentHome studentHome = shRepo.findByLocationNameAndName(rule.getStudentHome().getLocationName(), rule.getStudentHome().getName()).get();
			rule.setStudentHome(studentHome);
			ruleRepo.saveAndFlush(rule);
		}
		String.valueOf(response.getStatus());
		return "true";
	}
}
