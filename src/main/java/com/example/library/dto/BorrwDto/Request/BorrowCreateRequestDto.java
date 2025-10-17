package com.example.library.dto.BorrwDto.Request;

import com.example.library.common.enums.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowCreateRequestDto {
    private Long memberId;
    private Long bookId;
    private Result result;
    private String message;
}
