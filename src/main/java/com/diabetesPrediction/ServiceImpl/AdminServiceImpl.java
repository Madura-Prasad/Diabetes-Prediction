package com.diabetesPrediction.ServiceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.diabetesPrediction.Model.Book;
import com.diabetesPrediction.Model.Message;
import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.BookRepo;
import com.diabetesPrediction.Repository.MessageRepo;
import com.diabetesPrediction.Repository.UserRepo;
import com.diabetesPrediction.Service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User saveRoles(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		String currentMonth = java.time.LocalDate.now().getMonth().toString();
		user.setCreateYear(currentMonth);
		User newRole = userRepo.save(user);
		return newRole;
	}

	@Override
	public User getUserByID(Long id) {
		return userRepo.findById(id).get();
	}

	@Override
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public void deleteUserByID(Long id) {
		userRepo.deleteById(id);

	}

	@Override
	public List<Message> getAllMessages() {
		return messageRepo.findAll();
	}

	@Override
	public List<Book> getAllAppointments() {
		return bookRepo.findAll();
	}

}
