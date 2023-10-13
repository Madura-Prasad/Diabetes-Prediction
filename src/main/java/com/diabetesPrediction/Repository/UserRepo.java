package com.diabetesPrediction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diabetesPrediction.Model.User;

public interface UserRepo extends JpaRepository<User, Long>{

	public User findByEmail(String email);
}
