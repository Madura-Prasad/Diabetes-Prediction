package com.diabetesPrediction.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

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
	public String messages() {
		return "AdminPanel/messages";
	}

	@PostMapping("/saveRole")
	public String saveRole(@ModelAttribute User user, HttpSession session) {
		User existingRole = userRepo.findByEmail(user.getEmail());

		if (existingRole != null) {
			session.setAttribute("roleMsgError", "Email address already exists. Please use a different email.");
			session.removeAttribute("msg");
			return "redirect:/admin/addUsers";
		} else {
			User saveUser = adminService.saveRoles(user);
			return "redirect:/admin/users";
		}
	}
	
	@GetMapping("/editUsers/{id}")
	public String editProducts(@PathVariable Long id,Model model) {
		model.addAttribute("user", adminService.getUserByID(id));
		return "AdminPanel/editPatient";
	}
	
	@PostMapping("/editUsers/{id}")
	public String updateAdmin(@PathVariable Long id,@ModelAttribute("user") User user,Model model )
	{
		User existingUser = adminService.getUserByID(id);
		existingUser.setId(id);
		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		existingUser.setState(user.getState());
		existingUser.setMobile(user.getMobile());
		existingUser.setRole(user.getRole());
		
		
		// Check if a new password is provided
	    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
	        // Hash the new password
	        String hashedPassword = passwordEncoder.encode(user.getPassword());

	        // Set the hashed password in the user object
	        existingUser.setPassword(hashedPassword);
	    }
	    
	    existingUser.setCreateYear(user.getCreateYear());

		adminService.updateUser(existingUser);
		return "redirect:/admin/users";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable Long id) {
		adminService.deleteUserByID(id);
		return "redirect:/admin/users";
	}

}
