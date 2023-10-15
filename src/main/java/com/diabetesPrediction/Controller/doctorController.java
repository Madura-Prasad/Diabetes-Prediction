package com.diabetesPrediction.Controller;

import java.security.Principal;
import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.diabetesPrediction.Model.Book;
import com.diabetesPrediction.Model.DoctorDetails;
import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.BookRepo;
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

	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private JavaMailSender javaMailSender;

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

				// Fetch the appointments for the logged-in doctor
				List<Book> doctorAppointments = bookRepo.findByDoctorName(details.getName());
				model.addAttribute("doctorAppointments", doctorAppointments);

				// Log an INFO message for successfully displaying doctor appointments
				logger.info("Displayed appointments for doctor: " + details.getName());
			}

			return "DoctorPanel/appointments";
		} catch (Exception e) {
			// Log the exception with an ERROR level
			logger.error("An error occurred while displaying doctor appointments", e);

			// Log a WARN message for the display failure
			logger.warn("Failed to display doctor appointments");

			return "redirect:/errorPage";
		}
	}

	@GetMapping("/appointments/approve/{id}")
	public String editProducts(@PathVariable Long id, Model model, Principal principal) {
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
			}

			Book doctorAppointments = doctorDetailsService.getappointmentByID(id);
			model.addAttribute("doctorAppointments", doctorAppointments);

			// Log an INFO message for successfully displaying doctor's appointment for
			// editing
			logger.info("Displayed doctor's appointment for editing with ID: " + id);

			return "DoctorPanel/approve";
		} catch (Exception e) {
			// Log the exception with an ERROR level
			logger.error("An error occurred while displaying doctor's appointment for editing with ID: " + id, e);

			// Log a WARN message for the display failure
			logger.warn("Failed to display doctor's appointment for editing with ID: " + id);

			return "redirect:/errorPage";
		}
	}

	@PostMapping("/appointments/approve/{id}")
	public String updateAdmin(@PathVariable Long id, @ModelAttribute("user") Book book, Model model) {
		try {
			Book approve = doctorDetailsService.getappointmentByID(id);
			approve.setId(id);
			approve.setUserName(book.getUserName());
			approve.setUserEmail(book.getUserEmail());
			approve.setUserMobile(book.getUserMobile());
			approve.setDoctorName(book.getDoctorName());
			approve.setDoctorEmail(book.getDoctorEmail());
			approve.setDoctorAvailable(book.getDoctorAvailable());
			approve.setDoctorSpecificAre(book.getDoctorSpecificAre());
			approve.setStatus("Approved");

			bookRepo.save(approve);

			// Send a welcome email to the user
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setSubject("Doctor Approved Your Appointment Successfully!");
			mailMessage.setTo(book.getUserEmail());
			mailMessage.setFrom("docfinder.xyz@gmail.com");

			String emailContent = "Dear " + book.getUserName() + ",\n\n"
					+ "We are pleased to inform you that your appointment has been approved.\n\n"
					+ "Here are the details:\n" + "Doctor Name: " + book.getDoctorName() + "\n" + "Doctor Email: "
					+ book.getDoctorEmail() + "\n" + "Doctor Available Date: " + book.getDoctorAvailable() + "\n"
					+ "If you have any questions or need to make changes, please don't hesitate to contact us.\n\n"
					+ "Thank you for choosing DocFinder.\n\n" + "Sincerely,\n" + "The DocFinder Team";

			mailMessage.setText(emailContent);

			javaMailSender.send(mailMessage);

			// Log an INFO message for successfully approving the appointment
			logger.info("Approved appointment with ID: " + id + " for user: " + book.getUserName());

			return "redirect:/doctor/appointments";
		} catch (Exception e) {
			// Log the exception with an ERROR level
			logger.error("An error occurred while approving the appointment with ID: " + id, e);

			// Log a WARN message for the approval failure
			logger.warn("Failed to approve the appointment with ID: " + id);

			return "redirect:/errorPage";
		}
	}

	@GetMapping("/appointments/decline/{id}")
	public String declineApprovement(@PathVariable Long id, Model model, Principal principal) {
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
			}

			Book doctorAppointments = doctorDetailsService.getappointmentByID(id);
			model.addAttribute("doctorAppointments", doctorAppointments);

			// Log an INFO message for accessing the decline approval page
			logger.info("Accessed the decline approval page for appointment with ID: " + id);

			return "DoctorPanel/decline";
		} catch (Exception e) {
			// Log the exception with an ERROR level
			logger.error("An error occurred while accessing the decline approval page for appointment with ID: " + id,
					e);

			// Log a WARN message for the access failure
			logger.warn("Failed to access the decline approval page for appointment with ID: " + id);

			return "redirect:/errorPage";
		}
	}

	@PostMapping("/appointments/decline/{id}")
	public String declineAppointment(@PathVariable Long id, @ModelAttribute("user") Book book, Model model) {
		try {
			Book approve = doctorDetailsService.getappointmentByID(id);
			approve.setId(id);
			approve.setUserName(book.getUserName());
			approve.setUserEmail(book.getUserEmail());
			approve.setUserMobile(book.getUserMobile());
			approve.setDoctorName(book.getDoctorName());
			approve.setDoctorEmail(book.getDoctorEmail());
			approve.setDoctorAvailable(book.getDoctorAvailable());
			approve.setDoctorSpecificAre(book.getDoctorSpecificAre());
			approve.setStatus("Declined");

			bookRepo.save(approve);

			// Send a decline email to the user
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setSubject("Doctor Declined Your Appointment");
			mailMessage.setTo(book.getUserEmail());
			mailMessage.setFrom("docfinder.xyz@gmail.com");

			String emailContent = "Dear " + book.getUserName() + ",\n\n"
					+ "We regret to inform you that your appointment has been declined.\n\n"
					+ "Here were the details:\n" + "Doctor Name: " + book.getDoctorName() + "\n" + "Doctor Email: "
					+ book.getDoctorEmail() + "\n" + "Doctor Available Date: " + book.getDoctorAvailable() + "\n"
					+ "If you have any questions or would like to reschedule, please don't hesitate to contact us.\n\n"
					+ "We apologize for any inconvenience, and we hope to assist you with your healthcare needs in the future.\n\n"
					+ "Sincerely,\n" + "The DocFinder Team";

			mailMessage.setText(emailContent);

			javaMailSender.send(mailMessage);

			// Log an INFO message for successfully declining the appointment
			logger.info("Declined appointment with ID: " + id + " for user: " + book.getUserName());

			return "redirect:/doctor/appointments";
		} catch (Exception e) {
			// Log the exception with an ERROR level
			logger.error("An error occurred while declining the appointment with ID: " + id, e);

			// Log a WARN message for the decline failure
			logger.warn("Failed to decline the appointment with ID: " + id);

			return "redirect:/errorPage";
		}
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
