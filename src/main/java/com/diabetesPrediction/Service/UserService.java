package com.diabetesPrediction.Service;


import java.util.List;

import com.diabetesPrediction.Model.Book;
import com.diabetesPrediction.Model.DoctorDetails;
import com.diabetesPrediction.Model.Message;
import com.diabetesPrediction.Model.User;

public interface UserService {

	public User saveUser(User user);

	public Message saveMessages(Message message);

	public void removeSessionMessage();
	
	List<DoctorDetails> getAllDoctors();
	
	DoctorDetails getDoctorDetailsByID(Long id);
	
	public Book saveBook(Book book);

}
