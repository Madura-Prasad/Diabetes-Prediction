package com.diabetesPrediction.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String userName;
	private String userEmail;
	private String userMobile;
	private String doctorName;
	private String doctorEmail;
	private String doctorSpecificAre;
	private String doctorAvailable;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getDoctorEmail() {
		return doctorEmail;
	}
	public void setDoctorEmail(String doctorEmail) {
		this.doctorEmail = doctorEmail;
	}
	public String getDoctorSpecificAre() {
		return doctorSpecificAre;
	}
	public void setDoctorSpecificAre(String doctorSpecificAre) {
		this.doctorSpecificAre = doctorSpecificAre;
	}
	public String getDoctorAvailable() {
		return doctorAvailable;
	}
	public void setDoctorAvailable(String doctorAvailable) {
		this.doctorAvailable = doctorAvailable;
	}

	
	
	
}
