package com.rory.app.library.Repo;

import com.rory.app.library.Models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
}
