package com.example.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "borrow_log")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
