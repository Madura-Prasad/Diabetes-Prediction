package com.diabetesPrediction.Service;


import com.diabetesPrediction.Model.Message;
import com.diabetesPrediction.Model.User;

public interface UserService {

	public User saveUser(User user);

	public Message saveMessages(Message message);

	public void removeSessionMessage();

}
