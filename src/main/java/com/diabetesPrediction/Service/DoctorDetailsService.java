package com.diabetesPrediction.Service;

import java.util.List;

import com.diabetesPrediction.Model.Book;
import com.diabetesPrediction.Model.DoctorDetails;

public interface DoctorDetailsService {
	
	public DoctorDetails saveDetails(DoctorDetails doctorDetails);
	
	 List<DoctorDetails> getAllDetails();
	 
	 Book getappointmentByID(Long id);
	
}
