package com.diabetesPrediction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diabetesPrediction.Model.Message;
import com.diabetesPrediction.Repository.MessageRepo;


@SpringBootTest
public class UserUnitTest {
	
	@Autowired
	MessageRepo messageRepo;

	
	@Test
	public void testMessageSave() {
		Message message = new Message();
		message.setId(2L);
		message.setName("Name");
		message.setEmail("Doctor@gmail.com");
		message.setSubject("0714547852");
		message.setMessage("Stress");
		messageRepo.save(message);
		assertNotNull(messageRepo.findById(2L).get());
	}

	@Test
	public void testReadAllDetailst() {
		List<Message> list1 = messageRepo.findAll();
		assertThat(list1).size().isGreaterThan(0);
	}
}
