package com.diabetesPrediction.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.diabetesPrediction.Model.Book;

public interface BookRepo extends JpaRepository<Book, Long>{

	 List<Book> findByDoctorName(String doctorName);
}
