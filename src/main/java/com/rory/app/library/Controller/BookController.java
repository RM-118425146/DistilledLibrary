package com.rory.app.library.Controller;

import com.rory.app.library.Models.Book;
import com.rory.app.library.Models.Borrow;
import com.rory.app.library.Repo.BookRepo;
import com.rory.app.library.Repo.BorrowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private BorrowRepo borrowRepo;

    //sends list of all books in database
    @GetMapping(value = "/Books")
    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    //saves book to database
    @PostMapping(value = "/AddBook")
    public String saveBook(@RequestBody Book book){
        bookRepo.save(book);
        return "Added....";
    }

    //updates existing book in database
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

    //updates book in database to borrowed status if available
    //if unavailable, user is notified
    //a borrow is created for the book if available
    @PutMapping(value ="/BorrowBook/{id}")
    public String borrowBook(@PathVariable long id, @RequestBody Borrow borrow){
        Book updatedBook = bookRepo.findById(id).get();
        String status = updatedBook.getStatus();
        if(status.matches("BORROWED")) {
            return "Book not currently available....";
        }else{
            updatedBook.setStatus("BORROWED");
            bookRepo.save(updatedBook);
            borrowRepo.save(borrow);
            return "Borrowed....";
        }
    }

    //book is updated from borrowed to available
    @PatchMapping(value ="/ReturnBook/{id}")
    public String returnBook(@PathVariable long id){
        Book updatedBook = bookRepo.findById(id).get();
        updatedBook.setStatus("AVAILABLE");
        bookRepo.save(updatedBook);
        return "Returned....";
    }

    //book is deleted from database
    @DeleteMapping(value = "/DeleteBook/{id}")
    public String deleteBook(@PathVariable long id){
        Book deleteBook = bookRepo.findById(id).get();
        bookRepo.delete(deleteBook);
        return "Deleted....";
    }

    //finds book with matching isbn
    //if book is found then it is sent as a string
    //if no book is found then user is notified
    @GetMapping(value = "/SearchBookISBN/{isbn}")
    public String getBookISBN(@PathVariable String isbn){
        List<Book> Books = new ArrayList<>();
        Books = bookRepo.findAll();
        for (Book book:Books) {
            if(book.getIsbn().matches(isbn)){
                return book.toString();
            }
        }
        return "Book Not Found....";
    }

    //finds book with matching title
    //if book is found then it is sent as a string
    //if no book is found then user is notified
    @GetMapping(value = "/SearchBookTitle/{title}")
    public String getBookTitle(@PathVariable String title){
        List<Book> Books = new ArrayList<>();
        Books = bookRepo.findAll();
        for (Book book:Books) {
            if(book.getTitle().matches(title)){
                return book.toString();
            }
        }
        return "Book Not Found....";
    }

    //finds book containing name provided
    //name does not have to be an exact match i.e not case sensitive, last name or first name not required
    //if books are found then they sent as a string
    //if no book is found then user is notified
    @GetMapping(value = "/SearchBookAuthor/{author}")
    public String getBookAuthor(@PathVariable String author) {
        List<Book> allBooks = new ArrayList<>();
        List<Book> Books = new ArrayList<>();
        allBooks = bookRepo.findAll();
        for (Book book : allBooks) {
            if (book.getAuthors().toUpperCase().contains(author.toUpperCase())) {
                Books.add(book);
            }
        }
        if (Books.isEmpty()) {
            return "Book Not Found....";
        } else {
            return Books.toString();
        }
    }

}
