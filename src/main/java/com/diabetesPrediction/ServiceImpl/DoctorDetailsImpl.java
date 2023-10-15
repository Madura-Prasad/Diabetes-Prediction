package com.diabetesPrediction.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.diabetesPrediction.Service.DoctorDetailsService;
import com.diabetesPrediction.Model.DoctorDetails;
import com.diabetesPrediction.Repository.DoctorDetailsRepo;

@Service
public class DoctorDetailsImpl implements DoctorDetailsService{
	
	@Autowired 
	private DoctorDetailsRepo detailsRepo;

	@Override
	public DoctorDetails saveDetails(DoctorDetails doctorDetails) {
		return detailsRepo.save(doctorDetails);
	}

	@Override
	public List<DoctorDetails> getAllDetails() {
		return detailsRepo.findAll();
	}
	


}
