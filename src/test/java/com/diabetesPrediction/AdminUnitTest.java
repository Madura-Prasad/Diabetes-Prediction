package com.diabetesPrediction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diabetesPrediction.Model.User;
import com.diabetesPrediction.Repository.UserRepo;



@SpringBootTest
public class AdminUnitTest {
	
	
	@Autowired
	UserRepo userRepo;

	@Test
	public void testAdminSave() {
		User user = new User();
		user.setId(2L);
		user.setName("First Name");
		user.setEmail("Test@gmail.com");
		user.setPassword("password");
		user.setRole("ROLE_USER");
		user.setCreateYear("11-10-2023");
		user.setState("Colombo");
		user.setMobile("0771452456");
		userRepo.save(user);
		assertNotNull(userRepo.findById(2L).get());
	}

	@Test
	public void testReadAllUser() {
		List<User> list1 = userRepo.findAll();
		assertThat(list1).size().isGreaterThan(0);
	}

	@Test
	public void testUpdateProduct() {
		User user = userRepo.findById(2L).get();
		user.setName("Test");
		userRepo.save(user);
		assertNotEquals("First Name", userRepo.findById(2L).get().getName());
	}

	@Test
	public void testDelete() {
		userRepo.deleteById(1L);
		assertThat(userRepo.existsById(1L)).isFalse();
	}

}
