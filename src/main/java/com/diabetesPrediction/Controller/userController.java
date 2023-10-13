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
@RequestMapping("/user/")
public class userController {

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/account")
	public String account(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "account";
	}
	
	@GetMapping("/book")
	public String bookAppointment(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "bookingDetails";
	}
}
