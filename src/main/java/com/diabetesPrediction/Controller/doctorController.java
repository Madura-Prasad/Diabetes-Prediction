package com.diabetesPrediction.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequestMapping("/doctor/")
public class doctorController {

	
	@GetMapping("/")
	public String dashboard() {
		return "DoctorPanel/index-2";
	}
	
	@GetMapping("/addDetails")
	public String addDoctorDetails() {
		return "DoctorPanel/addDoctor";
	}
	
	@GetMapping("/appointments")
	public String appointments() {
		return "DoctorPanel/appointments";
	}
	
}
