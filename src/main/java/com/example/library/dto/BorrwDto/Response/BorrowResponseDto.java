package com.example.library.dto.BorrwDto.Response;

import com.example.library.common.enums.Result;

import java.time.LocalDateTime;

public class BorrowResponseDto {
    private Long logId;
    private Long memberId;
    private String memberName;
    private Long bookId;
    private String bookTitle;
    private Result result;
    private String message;
    private LocalDateTime createdAt;
}
