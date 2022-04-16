package com.rory.app.library.Repo;

import com.rory.app.library.Models.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepo extends JpaRepository<Borrow, Long> {
}
