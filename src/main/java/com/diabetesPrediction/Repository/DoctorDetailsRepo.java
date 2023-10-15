package com.diabetesPrediction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diabetesPrediction.Model.DoctorDetails;

public interface DoctorDetailsRepo extends JpaRepository<DoctorDetails, Long>{
	public DoctorDetails findByEmail(String email);
}
