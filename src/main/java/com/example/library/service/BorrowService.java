package com.example.library.service;

import com.example.library.dto.BorrwDto.Request.BorrowCreateRequestDto;
import com.example.library.dto.BorrwDto.Response.BorrowResponseDto;
import jakarta.validation.constraints.Positive;

public interface BorrowService {

    BorrowResponseDto borrow(@Positive(message = "memberID는 1이상 정수") Long memberId, @Positive(message = "bookId는 1이상 정수") Long bookId, BorrowCreateRequestDto dto);
}
