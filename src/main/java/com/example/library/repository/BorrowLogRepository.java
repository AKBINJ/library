package com.example.library.repository;

import com.example.library.entity.BorrowLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowLogRepository extends JpaRepository<BorrowLog, Long> {
}
