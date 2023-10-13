package com.diabetesPrediction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diabetesPrediction.Model.Message;

public interface MessageRepo extends JpaRepository<Message, Long>{

}
