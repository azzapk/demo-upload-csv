package com.azzapk.demouploadcsv.utility;

import com.azzapk.demouploadcsv.entity.Book;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    private static final String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file){
        if (!TYPE.equals(file.getContentType())){
            return false;
        }
        return true;
    }

    public static List<Book> csvToBooks(InputStream is){
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            CSVParser parser = new CSVParser(fileReader, CSVFormat
                    .DEFAULT.withFirstRecordAsHeader()
                    .withIgnoreHeaderCase().withTrim());

            List<Book> books = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = parser.getRecords();

            for (CSVRecord csvRecord: csvRecords){
                Book book = new Book();
                book.setId(Long.parseLong(csvRecord.get("Id")));
                book.setTitle(csvRecord.get("Title"));
                book.setDescription(csvRecord.get("Description"));
                book.setPrice(Double.parseDouble(csvRecord.get("Price")));
                books.add(book);
            }

            parser.close();
            return books;

        } catch (IOException ex) {
            throw new RuntimeException("Fail to parse CSV file: "+ex.getMessage());
        }
    }
}
