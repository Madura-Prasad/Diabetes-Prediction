package com.diabetesPrediction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diabetesPrediction.Model.Book;

public interface BookRepo extends JpaRepository<Book, Long>{

}
