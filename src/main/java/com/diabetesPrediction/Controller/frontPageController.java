package com.diabetesPrediction.Controller;

import java.security.Principal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.diabetesPrediction.Model.Message;
import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.MessageRepo;
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
	
	@Autowired
	private MessageRepo messageRepo;
	
	private static final Logger logger = LogManager.getLogger(frontPageController.class);

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
	        try {
	            User existingUser = userRepo.findByEmail(user.getEmail());

	            if (existingUser != null) {
	                session.setAttribute("msgError", "Email address already exists. Please use a different email.");
	                session.removeAttribute("msg");
	                // Log an INFO message
	                logger.info("Registration failed for user with email: " + user.getEmail() + " - Email already exists");
	                return "redirect:/signup";
	            } else {
	                User savedUser = userService.saveUser(user);

	                if (savedUser != null) {
	                    session.setAttribute("msg", "Registration successful. Please sign in.");
	                    session.removeAttribute("msgError");

	                    // Log an INFO message for successful registration
	                    logger.info("User registered successfully with email: " + user.getEmail());

	                    // Send a welcome email to the user
	                    SimpleMailMessage mailMessage = new SimpleMailMessage();
	                    mailMessage.setSubject("Welcome to DocFinder, " + user.getName() + "! Registration Successful");
	                    mailMessage.setTo(user.getEmail());
	                    mailMessage.setFrom("maduraprasa00@gmail.com");
	                    String emailContent = "Dear " + user.getName() + ",\n\n" +
	                            "Thank you for registering with DocFinder. Your registration was successful, and we're excited to welcome you to our platform!\n\n" +
	                            // Include email content
	                            "Sincerely,\n" +
	                            "The DocFinder Team";

	                    mailMessage.setText(emailContent);

	                    javaMailSender.send(mailMessage);

	                    return "redirect:/signup";
	                } else {
	                    session.setAttribute("msgError", "Something went wrong on the server.");
	                    session.removeAttribute("msg");
	                    // Log a WARN message for registration failure
	                    logger.warn("Registration failed for user with email: " + user.getEmail());
	                    return "redirect:/signup";
	                }
	            }
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while processing the registration request for user with email: " + user.getEmail(), e);
	            
	            return "redirect:/errorPage";
	        }
	    }
	
	
	 @PostMapping("/sendMessage")
	    public String saveMessage(@ModelAttribute Message message, HttpSession session) {
	        try {
	            Message sentMessage = messageRepo.save(message);
	            
	            // Log an INFO message for a successful message save
	            logger.info("Message sent successfully with Name: " + sentMessage.getName());
	            
	            return "redirect:/contact";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while saving the message", e);
	            
	            // Log a WARN message for the message save failure
	            logger.warn("Failed to save the message.");
	            
	            return "redirect:/errorPage"; 
	        }
	    }
	
	
}
