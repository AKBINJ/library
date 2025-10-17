package com.example.library.dto.MemberDto.Response;

import com.example.library.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long memberId;
    private String name;
    private Status status;
}
