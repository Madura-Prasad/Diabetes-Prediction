package com.diabetesPrediction.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diabetesPrediction.Service.DoctorDetailsService;
import com.diabetesPrediction.Model.Book;
import com.diabetesPrediction.Model.DoctorDetails;
import com.diabetesPrediction.Repository.BookRepo;
import com.diabetesPrediction.Repository.DoctorDetailsRepo;

@Service
public class DoctorDetailsImpl implements DoctorDetailsService{
	
	@Autowired 
	private DoctorDetailsRepo detailsRepo;
	
	@Autowired 
	private BookRepo bookRepo;

	@Override
	public DoctorDetails saveDetails(DoctorDetails doctorDetails) {
		return detailsRepo.save(doctorDetails);
	}

	@Override
	public List<DoctorDetails> getAllDetails() {
		return detailsRepo.findAll();
	}
	
	
	public List<Book> getBooksForLoggedInDoctor(String doctorName) {
	    return bookRepo.findByDoctorName(doctorName);
	}

	@Override
	public Book getappointmentByID(Long id) {
		return bookRepo.findById(id).get();
	}


}
