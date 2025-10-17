package com.example.library.controller;

import com.example.library.dto.BorrwDto.Request.BorrowCreateRequestDto;
import com.example.library.dto.BorrwDto.Response.BorrowResponseDto;
import com.example.library.service.BookService;
import com.example.library.service.BorrowService;
import com.example.library.service.MemberService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
public class BorrowController {
    private final BookService bookService;
    private final BorrowService borrowService;
    private final MemberService memberService;

    @PostMapping("/borrow")
    public ResponseEntity<BorrowResponseDto> borrow(
            @PathVariable("memberId") @Positive(message = "memberID는 1이상 정수")Long memberId,
            @PathVariable("bookId") @Positive(message = "bookId는 1이상 정수")Long bookId,
            @RequestBody BorrowCreateRequestDto dto
            ) {
        BorrowResponseDto result = borrowService.borrow(memberId, bookId, dto);
        return ResponseEntity.ok().body(result);
    }


}
