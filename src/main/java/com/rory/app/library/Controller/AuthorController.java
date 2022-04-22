package com.rory.app.library.Controller;

import com.rory.app.library.Models.Author;
import com.rory.app.library.Repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    private AuthorRepo authorRepo;

    //sends list of all authors
    @GetMapping(value = "/Authors")
    public List<Author> getAuthors(){
        return authorRepo.findAll();
    }

    //saves author to database
    @PostMapping(value = "/AddAuthor")
    public String saveAuthor(@RequestBody Author author){
        authorRepo.save(author);
        return "Added....";
    }
}
