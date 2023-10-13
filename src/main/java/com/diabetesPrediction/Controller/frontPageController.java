package com.diabetesPrediction.Controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.UserRepo;
import com.diabetesPrediction.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("*")
public class frontPageController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@GetMapping("/")
	public String Index(Model model, Principal principal){
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "index";
	}
	
	@GetMapping("/about")
	public String about(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "about";
	}
	
	@GetMapping("/doctors")
	public String doctors(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "doctorList";
	}
	
	@GetMapping("/faq")
	public String faq(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "faq";
	}
	
	@GetMapping("/privacy")
	public String privayPolicy(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "privacyPolicy";
	}
	
	@GetMapping("/terms")
	public String termsConditions(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "termsCondition";
	}
	
	@GetMapping("/contact")
	public String contact(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "contact";
	}
	
	@GetMapping("/signin")
	public String singIn(Model model) {
		return "signIn";
	}
	
	@GetMapping("/signup")
	public String singUp(Model model, Principal principal) {
		
		return "signUp";
	}
	
	@GetMapping("/doctor-list")
	public String doctorList(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "doctorList";
	}
	
	
	
	@GetMapping("/404")
	public String pageNotFound(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user =userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "404";
	}
	
	
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session) {
		User existingUser = userRepo.findByEmail(user.getEmail());

		if (existingUser != null) {
			session.setAttribute("msgError", "Email address already exists. Please use a different email.");
			session.removeAttribute("msg");
			return "redirect:/signup";
		} else {
			User savedUser = userService.saveUser(user);
			

			if (savedUser != null) {
				session.setAttribute("msg", "Registration successful. Please sign in.");
				session.removeAttribute("msgError");
				
				
				SimpleMailMessage mailMessage=new SimpleMailMessage();
				mailMessage.setSubject("Welcome to DocFinder, " + user.getName() + "! Registration Successful");
				mailMessage.setTo(user.getEmail());
				mailMessage.setFrom("maduraprasa00@gmail.com");
				String emailContent = "Dear " + user.getName() + ",\n\n" +
				        "Thank you for registering with DocFinder. Your registration was successful, and we're excited to welcome you to our platform!\n\n" +
				        
				        "Here's what you can do with your DocFinder account:\n" +
				        "• Search for healthcare providers in your area\n" +
				        "• Schedule appointments with doctors and specialists\n" +
				        "• Keep track of your medical history and upcoming appointments\n" +
				        "• Receive personalized health recommendations\n\n" +
				        
				        "To get started, simply log in to your DocFinder account using your email and the password you provided during registration. We take your privacy and security seriously, so please keep your password and email safe and do not share them with anyone.\n\n" +
				        
				        "We value your trust in us and are committed to providing you with a seamless healthcare experience. Your health and well-being are important to us.\n\n" +
				        
				        "Thank you for choosing DocFinder. We look forward to serving you and helping you take control of your healthcare journey.\n\n" +
				        
				        "Sincerely,\n" +
				        "The DocFinder Team";

				mailMessage.setText(emailContent);


				
				javaMailSender.send(mailMessage);
				//System.out.println(mailMessage.toString());
				
				
				return "redirect:/signup";
			} else {
				session.setAttribute("msgError", "Something went wrong on the server.");
				session.removeAttribute("msg");
				return "redirect:/signup";
			}
		}
	}
	
	
}
