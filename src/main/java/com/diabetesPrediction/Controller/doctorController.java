package com.diabetesPrediction.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.UserRepo;

@Controller
@CrossOrigin("*")
@RequestMapping("/doctor/")
public class doctorController {

	@Autowired
	private UserRepo userRepo;

	@GetMapping("/")
	public String dashboard(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "DoctorPanel/index-2";
	}

	@GetMapping("/addDetails")
	public String addDoctorDetails(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "DoctorPanel/addDoctor";
	}

	@GetMapping("/appointments")
	public String appointments() {
		return "DoctorPanel/appointments";
	}

}
