package com.diabetesPrediction.Service;

import com.diabetesPrediction.Model.User;

public interface UserService {

	public User saveUser(User user);

	public void removeSessionMessage();
	
	
}
