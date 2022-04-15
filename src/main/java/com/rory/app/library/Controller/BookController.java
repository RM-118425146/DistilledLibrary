package com.rory.app.library.Controller;

import com.rory.app.library.Models.Book;
import com.rory.app.library.Repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookRepo bookRepo;

    @GetMapping(value = "/Books")
    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    @PostMapping(value = "/SaveBook")
    public String saveBook(@RequestBody Book book){
        bookRepo.save(book);
        return "Saved....";
    }

    @PutMapping(value ="/UpdateBook/{id}")
    public String updateBook(@PathVariable long id, @RequestBody Book book){
        Book updatedBook = bookRepo.findById(id).get();
        updatedBook.setIsbn(book.getIsbn());
        updatedBook.setTitle(book.getTitle());
        updatedBook.setStatus(book.getStatus());
        updatedBook.setAuthors(book.getAuthors());
        bookRepo.save(updatedBook);
        return "Updated....";
    }

    @DeleteMapping(value = "/DeleteBook/{id}")
    public String deleteBook(@PathVariable long id){
        Book deleteBook = bookRepo.findById(id).get();
        bookRepo.delete(deleteBook);
        return "Deleted....";
    }

}
