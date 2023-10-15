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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.diabetesPrediction.Model.Book;
import com.diabetesPrediction.Model.DoctorDetails;
import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.BookRepo;
import com.diabetesPrediction.Repository.UserRepo;
import com.diabetesPrediction.Service.UserService;


@Controller
@CrossOrigin("*")
@RequestMapping("/user/")
public class userController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;
	
	
	@Autowired
	private BookRepo bookRepo;


	private static final Logger logger = LogManager.getLogger(userController.class);

	@GetMapping("/account")
	public String account(Model model, Principal principal) {
		try {
			boolean isLoggedIn = principal != null;

			model.addAttribute("isLoggedIn", isLoggedIn);

			if (isLoggedIn) {
				String email = principal.getName();
				User user = userRepo.findByEmail(email);
				model.addAttribute("user", user);
			}

			// Log an INFO message for accessing the account page
			logger.info("Accessed the account page for user: " + (isLoggedIn ? principal.getName() : "guest"));

			return "account";
		} catch (Exception e) {
			// Log the exception with an ERROR level
			logger.error("An error occurred while accessing the account page", e);

			// Log a WARN message for the access failure
			logger.warn("Failed to access the account page");

			return "redirect:/errorPage";
		}
	}
	
	
	  @GetMapping("/book/{id}")
	    public String bookAppointment(@PathVariable Long id, Model model, Principal principal) {
	        try {
	            boolean isLoggedIn = principal != null;

	            model.addAttribute("isLoggedIn", isLoggedIn);

	            if (isLoggedIn) {
	                String email = principal.getName();
	                User user = userRepo.findByEmail(email);
	                model.addAttribute("user", user);
	            }

	            DoctorDetails doctorDetails = userService.getDoctorDetailsByID(id);
	            model.addAttribute("doctorDetails", doctorDetails);

	            // Log an INFO message for accessing the booking details page
	            logger.info("Accessed the booking details page for doctor with ID: " + id + " for user: " + (isLoggedIn ? principal.getName() : "guest"));

	            return "bookingDetails";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while accessing the booking details page", e);

	            // Log a WARN message for the access failure
	            logger.warn("Failed to access the booking details page");

	            return "redirect:/errorPage";
	        }
	    }
	  
	  @PostMapping("/book/{id}")
	    public String saveBookingDetails(@ModelAttribute Book book, @PathVariable Long id, Model model) {
	        try {
	            DoctorDetails doctorDetails = userService.getDoctorDetailsByID(id);
	            book.setDoctorName(doctorDetails.getName());
	            book.setDoctorAvailable(doctorDetails.getAvailable());
	            book.setDoctorEmail(doctorDetails.getEmail());
	            book.setDoctorSpecificAre(doctorDetails.getSpecificArea());

	            Book savedBooking = bookRepo.save(book);

	            // Log an INFO message for successfully saving booking details
	            logger.info("Saved booking details for doctor with ID: " + id);

	            return "redirect:/doctors";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while saving booking details for doctor with ID: " + id, e);

	            // Log a WARN message for the save failure
	            logger.warn("Failed to save booking details for doctor with ID: " + id);

	            return "redirect:/errorPage";
	        }
	    }


	
	
}
