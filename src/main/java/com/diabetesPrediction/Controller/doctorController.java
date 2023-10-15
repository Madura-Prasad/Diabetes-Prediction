package com.diabetesPrediction.Controller;

import java.security.Principal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diabetesPrediction.Model.DoctorDetails;
import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.DoctorDetailsRepo;
import com.diabetesPrediction.Repository.UserRepo;
import com.diabetesPrediction.Service.DoctorDetailsService;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("*")
@RequestMapping("/doctor/")
public class doctorController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private DoctorDetailsService doctorDetailsService;

	@Autowired
	private DoctorDetailsRepo doctorDetailsRepo;

	private static final Logger logger = LogManager.getLogger(adminController.class);

	@GetMapping("/")
	public String dashboard(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;
		boolean isSaved = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);
		model.addAttribute("isSaved", isSaved);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		if (isSaved) {
			String email = principal.getName();
			DoctorDetails details = doctorDetailsRepo.findByEmail(email);
			model.addAttribute("doctor", details);
		}
		
		return "DoctorPanel/index-2";
	}
	
	@GetMapping("/addDetails")
    public String addDoctorDetails(Model model, Principal principal) {
        try {
            boolean isLoggedIn = principal != null;
            boolean isSaved = principal != null;

            model.addAttribute("isLoggedIn", isLoggedIn);
            model.addAttribute("isSaved", isSaved);

            if (isLoggedIn) {
                String email = principal.getName();
                User user = userRepo.findByEmail(email);
                model.addAttribute("user", user);
            }

            if (isSaved) {
                String email = principal.getName();
                DoctorDetails details = doctorDetailsRepo.findByEmail(email);
                model.addAttribute("doctor", details);

                // Log an INFO message for successfully displaying saved doctor details
                logger.info("Displayed saved doctor details for user: " + (isLoggedIn ? principal.getName() : "guest"));
            } else {
                // Log a WARN message for no saved doctor details found
                logger.warn("No saved doctor details found for user: " + (isLoggedIn ? principal.getName() : "guest"));
            }

            return "DoctorPanel/addDoctor";
        } catch (Exception e) {
            // Log the exception with an ERROR level
            logger.error("An error occurred while displaying doctor details", e);

            // Log a WARN message for the display failure
            logger.warn("Failed to display doctor details.");

            return "redirect:/errorPage";
        }
    }
	
	
	

	@GetMapping("/appointments")
	public String appointments(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;
		boolean isSaved = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);
		model.addAttribute("isSaved", isSaved);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		if (isSaved) {
			String email = principal.getName();
			DoctorDetails details = doctorDetailsRepo.findByEmail(email);
			model.addAttribute("doctor", details);
		}
		return "DoctorPanel/appointments";
	}

	@PostMapping("/saveDetails")
	public String saveDetails(@ModelAttribute DoctorDetails doctorDetails, HttpSession session, Model model) {
		try {
			DoctorDetails savedDetails = doctorDetailsService.saveDetails(doctorDetails);

			// Log an INFO message for successful details save
			logger.info("Doctor details saved successfully with Name : " + savedDetails.getName());

			return "redirect:/doctor/";
		} catch (Exception e) {
			// Log the exception with an ERROR level
			logger.error("An error occurred while saving doctor details", e);

			// Log a WARN message for the details save failure
			logger.warn("Failed to save doctor details.");

			return "redirect:/errorPage";
		}
	}

}
