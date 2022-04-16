package com.rory.app.library.Controller;

import com.rory.app.library.Models.Book;
import com.rory.app.library.Models.Borrow;
import com.rory.app.library.Repo.BookRepo;
import com.rory.app.library.Repo.BorrowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private BorrowRepo borrowRepo;

    @GetMapping(value = "/Books")
    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    @PostMapping(value = "/AddBook")
    public String saveBook(@RequestBody Book book){
        bookRepo.save(book);
        return "Added....";
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

    @PutMapping(value ="/BorrowBook/{id}")
    public String borrowBook(@PathVariable long id, @RequestBody Borrow borrow){
        Book updatedBook = bookRepo.findById(id).get();
        updatedBook.setStatus("BORROWED");
        bookRepo.save(updatedBook);
        borrowRepo.save(borrow);
        return "Borrowed....";
    }

    @PatchMapping(value ="/ReturnBook/{id}")
    public String returnBook(@PathVariable long id){
        Book updatedBook = bookRepo.findById(id).get();
        updatedBook.setStatus("AVAILABLE");
        bookRepo.save(updatedBook);
        return "Returned....";
    }

    @DeleteMapping(value = "/DeleteBook/{id}")
    public String deleteBook(@PathVariable long id){
        Book deleteBook = bookRepo.findById(id).get();
        bookRepo.delete(deleteBook);
        return "Deleted....";
    }

}
