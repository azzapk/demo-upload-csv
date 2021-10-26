package com.azzapk.demouploadcsv.repo;

import com.azzapk.demouploadcsv.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {

}
