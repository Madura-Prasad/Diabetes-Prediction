package com.diabetesPrediction.Controller;

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
import java.util.List;
import com.diabetesPrediction.Model.Message;
import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.UserRepo;
import com.diabetesPrediction.Service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("*")
@RequestMapping("/admin/")
public class adminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserRepo userRepo;
	
	private static final Logger logger = LogManager.getLogger(adminController.class);
	

	@GetMapping("/")
	public String dashboard() {
		return "AdminPanel/index-2";
	}

	@GetMapping("/users")
	public String users(Model model) {
		model.addAttribute("user", adminService.getAllUsers());
		return "AdminPanel/patients";
	}

	@GetMapping("/addUsers")
	public String addUsers() {
		return "AdminPanel/addPatient";
	}

	@GetMapping("/editUsers")
	public String editUsers() {
		return "AdminPanel/editPatient";
	}

	@GetMapping("/appointment")
	public String appointment() {
		return "AdminPanel/appointment";
	}
	
	 @GetMapping("/messages")
	    public String messages(Model model) {
	        try {
	            List<Message> messages = adminService.getAllMessages();
	            model.addAttribute("message", adminService.getAllMessages());
	            
	            // Log an INFO message for successful retrieval of messages
	            logger.info("Retrieved " + messages.size() + " messages");

	            return "AdminPanel/messages";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while retrieving messages", e);

	            // Log a WARN message for the retrieval failure
	            logger.warn("Failed to retrieve messages.");

	            return "redirect:/errorPage";
	        }
	    }

	@PostMapping("/saveRole")
    public String saveRole(@ModelAttribute User user, HttpSession session) {
        try {
            User existingRole = userRepo.findByEmail(user.getEmail());

            if (existingRole != null) {
                session.setAttribute("roleMsgError", "Email address already exists. Please use a different email.");
                session.removeAttribute("msg");
                // Log an INFO message
                logger.info("Email address already exists: " + user.getEmail());
                return "redirect:/admin/addUsers";
            } else {
                User saveUser = adminService.saveRoles(user);
                // Log a DEBUG message
                logger.debug("User saved successfully: " + saveUser.getEmail());
                return "redirect:/admin/users";
            }
        } catch (Exception e) {
            // Log the exception with an ERROR level
            logger.error("An error occurred while processing the request.", e);

            return "redirect:/errorPage";
        }
    }
	
	
	@GetMapping("/editUsers/{id}")
    public String editProducts(@PathVariable Long id, Model model) {
        try {
            User user = adminService.getUserByID(id);
            model.addAttribute("user", user);
            
            // Log an INFO message
            logger.info("Editing user with Name: " + user.getName());
            
            return "AdminPanel/editPatient";
        } catch (Exception e) {
            // Log the exception with an ERROR level
            logger.error("An error occurred while editing the user with ID: " + id, e);
            
            // Log a WARN message for the edit failure
            logger.warn("Failed to edit user with ID: " + id);
            
            return "redirect:/errorPage"; 
        }
    }
	

	    @PostMapping("/editUsers/{id}")
	    public String updateAdmin(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {
	        try {
	            User existingUser = adminService.getUserByID(id);
	            existingUser.setId(id);
	            existingUser.setName(user.getName());
	            existingUser.setEmail(user.getEmail());
	            existingUser.setState(user.getState());
	            existingUser.setMobile(user.getMobile());
	            existingUser.setRole(user.getRole());
	            existingUser.setPassword(user.getPassword());
	            existingUser.setCreateYear(user.getCreateYear());

	            // Log an INFO message before updating the user
	            logger.info("Updating user with Name: " + existingUser.getName());

	            adminService.updateUser(existingUser);

	            // Log an INFO message after updating the user
	            logger.info("User with Name " + existingUser.getName() + " updated successfully.");

	            return "redirect:/admin/users";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while updating the user with ID: " + id, e);
	            
	            // Log a WARN message for the user update failure
	            logger.warn("Failed to update user with ID: " + id);

	           
	            return "redirect:/errorPage";
	        }
	    }
	 

	    @GetMapping("/deleteUser/{id}")
	    public String deleteUser(@PathVariable Long id) {
	        try {
	            adminService.deleteUserByID(id);
	            
	            // Log an INFO message
	            logger.info("Deleted user with ID: " + id);
	            
	            return "redirect:/admin/users";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while deleting the user with ID: " + id, e);
	            
	            // Log a WARN message for the delete failure
	            logger.warn("Failed to delete user with ID: " + id);
	            
	            return "redirect:/errorPage"; 
	        }
	    }
}
