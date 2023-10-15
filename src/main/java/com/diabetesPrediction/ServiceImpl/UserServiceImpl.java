package com.diabetesPrediction.ServiceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.diabetesPrediction.Model.Book;
import com.diabetesPrediction.Model.DoctorDetails;
import com.diabetesPrediction.Model.Message;
import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.BookRepo;
import com.diabetesPrediction.Repository.DoctorDetailsRepo;
import com.diabetesPrediction.Repository.MessageRepo;
import com.diabetesPrediction.Repository.UserRepo;
import com.diabetesPrediction.Service.UserService;
import jakarta.servlet.http.HttpSession;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private DoctorDetailsRepo doctorDetailsRepo;
	
	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User saveUser(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setRole("ROLE_USER");
		String currentMonth = java.time.LocalDate.now().getMonth().toString();
		user.setCreateYear(currentMonth);
		User newUser= userRepo.save(user);
		return newUser;
	}

	@Override
	public void removeSessionMessage() {
		HttpSession session= ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg");
		session.removeAttribute("msgError");
	}

	@Override
	public Message saveMessages(Message message) {
		Message newMessage = messageRepo.save(message);
		return newMessage;
	}

	@Override
	public List<DoctorDetails> getAllDoctors() {
		return doctorDetailsRepo.findAll();
	}

	@Override
	public DoctorDetails getDoctorDetailsByID(Long id) {
		return doctorDetailsRepo.findById(id).get();
	}
	
	@Override
	public Book saveBook(Book book) {
		Book book2 = bookRepo.save(book);
		return book2;
	}
	


}
