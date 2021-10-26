package com.azzapk.demouploadcsv.controller;

import com.azzapk.demouploadcsv.dto.ResponseData;
import com.azzapk.demouploadcsv.entity.Book;
import com.azzapk.demouploadcsv.service.BookService;
import com.azzapk.demouploadcsv.utility.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<?> findAllBook(){
        ResponseData responseData = new ResponseData();

        try {
            List<Book> books = bookService.findAll();
            responseData.setStatus(true);
            responseData.getMessages().add("Get all books");
            responseData.setPayload(books);
            return ResponseEntity.ok(responseData);
        } catch (Exception ex){
            responseData.setStatus(false);
            responseData.getMessages().add("Could not get books: "+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file){
        ResponseData responseData = new ResponseData();

        if (!CSVHelper.hasCSVFormat(file)){
            responseData.setStatus(false);
            responseData.getMessages().add("Please upload a CSV File: "+ file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        try {
            List<Book> books = bookService.save(file);
            responseData.setStatus(true);
            responseData.getMessages().add("Upload :"+file.getOriginalFilename()+" successfully!");
            responseData.setPayload(books);
            return ResponseEntity.ok(responseData);
        } catch (Exception ex) {
            responseData.setStatus(false);
            responseData.getMessages().add("Could not upload the file: "+ file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseData);
        }
    }
}
