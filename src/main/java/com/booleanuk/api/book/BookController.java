package com.booleanuk.api.book;

import com.booleanuk.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        BookListResponse bookListResponse = new BookListResponse();
        bookListResponse.set(this.bookRepository.findAll());
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataStatus<?>> getById(@PathVariable int id){
        Book book = this.bookRepository.findById(id).orElse(null);

        if(book == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        BookResponse dataStatus = new BookResponse();
        dataStatus.set(book);

        return ResponseEntity.ok(dataStatus);
    }
}
