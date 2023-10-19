package com.diabetesPrediction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.diabetesPrediction.Model.Book;
import com.diabetesPrediction.Model.DoctorDetails;
import com.diabetesPrediction.Repository.BookRepo;
import com.diabetesPrediction.Repository.DoctorDetailsRepo;
import com.diabetesPrediction.Repository.UserRepo;

@SpringBootTest
public class DoctorUnitTest {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	DoctorDetailsRepo doctorDetailsRepo;
	
	@Autowired
	BookRepo bookRepo;
	
	@Test
	public void testDoctorDetailsSave() {
		DoctorDetails doctorDetails = new DoctorDetails();
		doctorDetails.setId(2L);
		doctorDetails.setName("Name");
		doctorDetails.setEmail("Doctor@gmail.com");
		doctorDetails.setMobile("0714547852");
		doctorDetails.setSpecificArea("Diabetes");
		doctorDetailsRepo.save(doctorDetails);
		assertNotNull(doctorDetailsRepo.findById(2L).get());
	}

	@Test
	public void testReadAllDetailst() {
		List<DoctorDetails> list1 = doctorDetailsRepo.findAll();
		assertThat(list1).size().isGreaterThan(0);
	}
	
	@Test
	public void testBookSave() {
		Book book = new Book();
		book.setId(2L);
		book.setUserName("Name");
		book.setUserEmail("Doctor@gmail.com");
		book.setUserMobile("0714547852");
		book.setDoctorName("Male");
		book.setDoctorEmail("Perera");
		book.setDoctorSpecificAre("Perera@gmail.com");
		book.setDoctorAvailable("07741452369");
		book.setStatus("Approved");
		bookRepo.save(book);
		assertNotNull(bookRepo.findById(2L).get());
	}
	

	@Test
	public void testReadAllBook() {
		List<Book> list1 = bookRepo.findAll();
		assertThat(list1).size().isGreaterThan(0);
	}
}
