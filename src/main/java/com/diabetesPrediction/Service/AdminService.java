package com.diabetesPrediction.Service;

import java.util.List;

import com.diabetesPrediction.Model.Message;
import com.diabetesPrediction.Model.User;

public interface AdminService {
	
	List<User> getAllUsers();
	
	public User saveRoles(User user);
	
	User getUserByID(Long id);
	
	User updateUser(User user);
	
	void deleteUserByID(Long id);
	
	List<Message> getAllMessages();

	
}
